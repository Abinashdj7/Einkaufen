package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(
			@RequestHeader(value = "Authorization", required = false) String jwt) throws UserException {

		System.out.println("/api/users/profile - Received JWT: "
				+ (jwt != null ? jwt.substring(0, Math.min(20, jwt.length())) + "..." : "null"));

		if (jwt == null || jwt.trim().isEmpty()) {
			throw new UserException("Authorization header is missing or empty");
		}

		try {
			User user = userService.findUserProfileByJwt(jwt);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			System.out.println("/api/users/profile - Invalid JWT: " + e.getMessage());
			throw new UserException("Invalid JWT token: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("/api/users/profile - Unexpected error: " + e.getMessage());
			e.printStackTrace();
			throw new UserException("Error retrieving user profile: " + e.getMessage());
		}
	}

}
