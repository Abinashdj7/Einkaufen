package com.Abinash.Nouveauecommerce.Config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

@Service
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret:}")
	private String configuredSecret;

	private SecretKey key;

	@PostConstruct
	private void init() {
		if (configuredSecret == null || configuredSecret.isBlank()) {
			logger.warn("jwt.secret is not configured - generating a random signing key for this run. "
					+ "Existing tokens will be invalidated on every restart; set the JWT_SECRET environment "
					+ "variable to use a stable, persistent signing key.");
			this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		} else {
			this.key = Keys.hmacShaKeyFor(configuredSecret.getBytes(StandardCharsets.UTF_8));
		}
	}

	public String generateToken(Authentication auth) {
		String authorities = auth.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		return Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", auth.getName())
				.claim("authorities", authorities)
				.signWith(key)
				.compact();
	}

	public Claims getClaimsFromToken(String jwt) throws IllegalArgumentException {
		String token = stripBearerPrefix(jwt);
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse JWT token: " + e.getMessage(), e);
		}
	}

	public String getEmailFromToken(String jwt) throws IllegalArgumentException {
		Claims claims = getClaimsFromToken(jwt);
		String email = String.valueOf(claims.get("email"));
		if (email == null || email.equals("null")) {
			throw new IllegalArgumentException("Email claim not found in JWT");
		}
		return email;
	}

	private String stripBearerPrefix(String jwt) {
		if (jwt == null || jwt.trim().isEmpty()) {
			throw new IllegalArgumentException("JWT token is null or empty");
		}
		if (jwt.length() < 7 || !jwt.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid JWT format: must start with 'Bearer '");
		}
		return jwt.substring(7);
	}
}
