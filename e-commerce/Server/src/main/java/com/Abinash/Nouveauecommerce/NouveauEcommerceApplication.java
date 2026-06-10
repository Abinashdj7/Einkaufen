package com.Abinash.Nouveauecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.Abinash.Nouveauecommerce.Controller.AuthController;

@SpringBootApplication
public class NouveauEcommerceApplication {

	private static final Logger logger = LoggerFactory.getLogger(NouveauEcommerceApplication.class);

	public static void main(String[] args) {
		logger.info("=================================================");
		logger.info("Starting Nouveau E-commerce Application");
		logger.info("=================================================");
		SpringApplication.run(NouveauEcommerceApplication.class, args);
		logger.info("=================================================");
		logger.info("Nouveau E-commerce Application Started Successfully");
		logger.info("=================================================");
	}

}
