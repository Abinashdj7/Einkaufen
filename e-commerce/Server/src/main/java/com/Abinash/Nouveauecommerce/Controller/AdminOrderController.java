package com.Abinash.Nouveauecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Service.OrderService;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrdersHandler() throws OrderException{
		List<Order> orders=orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.confirmedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/delivered")
	public ResponseEntity<Order> deliveredOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.deliveredOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/shipped")
	public ResponseEntity<Order> shippedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.shippedOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/cancelled")
	public ResponseEntity<Order> cancelledOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		Order order=orderService.cancelledOrder(orderId);
		return new ResponseEntity<Order>(order,HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<String> deletedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		orderService.deleteOrder(orderId);
		return new ResponseEntity<String>("Order deleted",HttpStatus.OK);
	}
}
