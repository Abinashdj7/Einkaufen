package com.Abinash.Nouveauecommerce.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Abinash.Nouveauecommerce.Exception.CartItemException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Service.CartItemService;
import com.Abinash.Nouveauecommerce.Service.UserService;

@CrossOrigin(origins = "*",allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api/cartItem")
public class CartItemController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartItemService cartItemService;
	
	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<String> deleteCartItem(@PathVariable Long cartItemId,@RequestHeader("Authorization") String jwt) 
			throws UserException,CartItemException{
		User user=userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	public ResponseEntity<CartItem>updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization")String jwt) throws CartItemException, UserException{
		
		User user=userService.findUserProfileByJwt(jwt);
		
		CartItem updatedCartItem =cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		//ApiResponse res=new ApiResponse("Item Updated",true);
		
		return new ResponseEntity<>(updatedCartItem,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/change/{cartItemId}")
	public ResponseEntity<String> changeQuantity(@PathVariable Long cartItemId,@RequestHeader("Authorization") String jwt,@RequestBody Integer changeNumber) throws CartItemException {
			cartItemService.changeNumberOfItems(cartItemId,changeNumber);
			return new ResponseEntity<String>("Item quantity changed by : "+changeNumber,HttpStatus.OK);
	}
}
