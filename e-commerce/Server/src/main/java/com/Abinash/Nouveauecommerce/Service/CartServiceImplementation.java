package com.Abinash.Nouveauecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.CartRepo;
import com.Abinash.Nouveauecommerce.Request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartItemService cartItemService;
	
	@Autowired
	ProductService productService;
	
	@Override
	public Cart createCart(User user) {
		Cart cart=new Cart();
		
		cart.setUser(user);
		
		return cartRepo.save(cart);
	}

	@Override
	public String addToCart(Long userId, AddItemRequest req) throws ProductException {
		Cart cart=cartRepo.findByUserId(userId);
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
		int price=req.getQuantity()*product.getDiscountedPrice();
		cartItem.setPrice(price);
		cartItem.setSize(req.getSize());
		
		CartItem createdCartItem=cartItemService.createCartItem(cartItem);
		cart.getCartItems().add(createdCartItem);
		
		}
		return "Item Add to cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepo.findByUserId(userId);
		Double totalPrice=0.0;
		Double totalDiscountedPrice=0.0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice+=cartItem.getPrice();
			totalDiscountedPrice+=cartItem.getDiscountedPrice();
			totalItem+=cartItem.getQuantity();
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		return cartRepo.save(cart);
	}
	
}
