package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
	@GetMapping("/")
	public String serverStatus() {
		return "Server is running";
	}
}
