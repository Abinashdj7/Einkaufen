package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
public class ServerController {
	@GetMapping("/")
	public String serverStatus() {
		return "Server is running";
	}
}
