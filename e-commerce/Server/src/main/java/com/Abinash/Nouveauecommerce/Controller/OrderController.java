package com.Abinash.Nouveauecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Address;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Service.OrderService;
import com.Abinash.Nouveauecommerce.Service.UserService;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserException{
		User user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.createOrder(user, shippingAddress);
		
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt)
	throws UserException, OrderException{
		User user=userService.findUserProfileByJwt(jwt);
		List<Order> orders=orderService.userOrderHistory(user.getId());
		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserException,OrderException{
		User user=userService.findUserProfileByJwt(jwt);
		Order order=orderService.findOrderById(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.CREATED);
		
	}
	
	
}
