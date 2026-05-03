package com.Abinash.Nouveauecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Model.Address;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Model.User;

@Service
public interface OrderService {
	public Order createOrder(User user,Address shippingAddress);
	
	public List<Order> userOrderHistory(Long orderId) throws OrderException;
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order deliveredOrder(Long orderId) throws OrderException;
	
	public Order cancelledOrder(Long orderId) throws OrderException;
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> getAllOrders() throws OrderException;
	
	public void deleteOrder(Long orderId) throws OrderException;
}
