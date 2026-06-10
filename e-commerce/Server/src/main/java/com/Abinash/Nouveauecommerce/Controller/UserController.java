package com.Abinash.Nouveauecommerce.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(
			@RequestHeader(value = "Authorization", required = false) String jwt) throws UserException {

		if (jwt == null || jwt.trim().isEmpty()) {
			throw new UserException("Authorization header is missing or empty");
		}

		try {
			User user = userService.findUserProfileByJwt(jwt);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			logger.warn("/api/users/profile - Invalid JWT: {}", e.getMessage());
			throw new UserException("Invalid JWT token: " + e.getMessage());
		} catch (Exception e) {
			logger.error("/api/users/profile - Unexpected error: {}", e.getMessage(), e);
			throw new UserException("Error retrieving user profile: " + e.getMessage());
		}
	}

}
