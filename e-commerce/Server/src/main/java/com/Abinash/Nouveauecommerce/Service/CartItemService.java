package com.Abinash.Nouveauecommerce.Service;

import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.CartItemException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.Product;


public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException,UserException;

	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId) throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
	
	public CartItem	changeNumberOfItems(Long cartItemId,Integer changeNumber) throws CartItemException;
	
	
}
