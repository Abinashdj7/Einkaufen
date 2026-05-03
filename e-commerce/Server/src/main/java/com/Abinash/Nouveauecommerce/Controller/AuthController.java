package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Config.JwtProvider;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.UserRepo;
import com.Abinash.Nouveauecommerce.Request.LoginRequest;
import com.Abinash.Nouveauecommerce.Response.AuthResponse;
import com.Abinash.Nouveauecommerce.Service.CartService;
import com.Abinash.Nouveauecommerce.Service.CustomerUserServiceImplementation;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
    JwtProvider jwtProvider;
	
	@Autowired
	CustomerUserServiceImplementation customerUserServiceImplementation;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		String email=user.getEmail();
		String password=user.getPassword();
		String firstString=user.getFirstName();
		String lastString=user.getLastName();
		System.out.println(userRepo);
		User isEmailExist=userRepo.findByEmail(email);
		if(isEmailExist!=null) {
			throw new UserException("Email is already used with another account");
		}
		User createdUser=new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFirstName(firstString);
		createdUser.setLastName(lastString);
		User savedUser=userRepo.save(createdUser);
		
		Cart cart=cartService.createCart(savedUser);
		
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Sign up successful");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
		
		String username=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(username,password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse=new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Sign in successful");
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED); 
	}

	public Authentication authenticate(String username, String password) {
		
		UserDetails userDetails=customerUserServiceImplementation.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("Invalid username");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
