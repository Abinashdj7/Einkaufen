package com.Abinash.Nouveauecommerce.Config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication auth) {
		String jwt = Jwts.builder()
				.setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 846000000))
				.claim("email", auth.getName()).signWith(key).compact();
		return jwt;
	}

	public String getEmailFromToken(String jwt) throws IllegalArgumentException {
		if (jwt == null || jwt.trim().isEmpty()) {
			throw new IllegalArgumentException("JWT token is null or empty");
		}
		if (jwt.length() < 7 || !jwt.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid JWT format: must start with 'Bearer '");
		}
		try {
			String token = jwt.substring(7);
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			String email = String.valueOf(claims.get("email"));
			if (email == null || email.equals("null")) {
				throw new IllegalArgumentException("Email claim not found in JWT");
			}
			return email;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to parse JWT token: " + e.getMessage(), e);
		}
	}
}
