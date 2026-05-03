package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Request.AddItemRequest;
import com.Abinash.Nouveauecommerce.Service.CartService;
import com.Abinash.Nouveauecommerce.Service.UserService;


@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		Cart cart=cartService.findUserCart(user.getId());
		
		System.out.println("cart - "+cart.getUser().getEmail());
		
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<String> addItemToCart(@RequestBody AddItemRequest req, 
			@RequestHeader("Authorization") String jwt) throws UserException, ProductException{
		System.out.println("Received request body: " + req);
		User user=userService.findUserProfileByJwt(jwt);
		
		String item = cartService.addToCart(user.getId(), req);
		
		return new ResponseEntity<>(item,HttpStatus.ACCEPTED);
		
	}
	

}
