package com.Abinash.Nouveauecommerce.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Request.AddItemRequest;

public interface CartService {
	public Cart createCart(User user);
	
	public String addToCart(Long userId,AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
}
