package com.Abinash.Nouveauecommerce.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.CartItemException;
import com.Abinash.Nouveauecommerce.Exception.UserException;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.CartItemRepo;
import com.Abinash.Nouveauecommerce.Repo.CartRepo;

@Service
public class CartItemServiceImplementation implements CartItemService{
	
	@Autowired
	CartItemRepo cartItemRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CartRepo cartRepo;
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		
		CartItem createdCartItem=cartItemRepo.save(cartItem);
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId()==userId) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		
		return cartItemRepo.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem=((CartItemRepo) cartItemRepo).isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem=findCartItemById(cartItemId);
		User user=userService.findUserById(cartItem.getUserId());
		
		User reqUser=userService.findUserById(userId);
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepo.deleteById(cartItemId);
		}
		else {
			throw new UserException("You cant remove another users item");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt=cartItemRepo.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("Cart item not found with this id");
	}

	@Override
	public CartItem changeNumberOfItems(Long cartItemId,Integer changeNumber) throws CartItemException {
		
		CartItem item=findCartItemById(cartItemId);
		item.setQuantity(item.getQuantity()+changeNumber);
		return null;
	}
	
}
