package com.Abinash.Nouveauecommerce.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.OrderException;
import com.Abinash.Nouveauecommerce.Model.Address;
import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.Order;
import com.Abinash.Nouveauecommerce.Model.OrderItem;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.AddressRepo;
import com.Abinash.Nouveauecommerce.Repo.CartItemRepo;
import com.Abinash.Nouveauecommerce.Repo.CartRepo;
import com.Abinash.Nouveauecommerce.Repo.OrderItemRepo;
import com.Abinash.Nouveauecommerce.Repo.OrderRepo;
import com.Abinash.Nouveauecommerce.Repo.UserRepo;

@Service
public class OrderServiceImplentation implements OrderService {

	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartItemService cartItemService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	AddressRepo addressRepo;
	
	@Autowired
	OrderRepo orderRepo;
	
	@Autowired
	OrderItemRepo orderItemRepo;
	
	@Autowired
	CartItemRepo cartItemRepo;
	
	@Override
	public Order createOrder(User user, Address shippingAddress) {
		shippingAddress.setUser(user);
		Address address=addressRepo.save(shippingAddress);
		user.getAddresses().add(address);
		userRepo.save(user);
		
		Cart cart=cartService.findUserCart(user.getId());
		List<OrderItem> orderItems=new ArrayList<>();
		for(CartItem item:cart.getCartItems()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setPrice(item.getPrice());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setSize(item.getSize());
			orderItem.setUserId(item.getUserId());
			orderItem.setDiscountedPrice(item.getDiscountedPrice());
			
			OrderItem createdOrderItem=orderItemRepo.save(orderItem);
			
			orderItems.add(createdOrderItem);
		}
		Order createdOrder=new Order();
		
		createdOrder.setUser(user);
		createdOrder.setOrderItems(orderItems);
		createdOrder.setTotalPrice(cart.getTotalPrice());
		createdOrder.setTotalDiscountPrice(cart.getTotalDiscountedPrice());
		createdOrder.setDiscount(cart.getDiscount());
		createdOrder.setTotalItems(cart.getTotalItem());
		
		createdOrder.setShippingAddress(address);
		createdOrder.setOrderDate(LocalDateTime.now());
		createdOrder.setOrderStatus("COMPLETED");
		createdOrder.getPaymentDetails().setPaymentStatus("Completed");
		createdOrder.setCreatedAt(LocalDateTime.now());
		Order savedOrder=orderRepo.save(createdOrder);
		cartItemRepo.clearCart(user.getId());
		//try {
		for(OrderItem item:orderItems) {
			item.setOrder(savedOrder);
			orderItemRepo.save(item);
		}
		return savedOrder;
		//}
		//catch(Exception e) {
			//e.printStackTrace();
		//}
		//return null;
	}

	@Override
	public List<Order> userOrderHistory(Long orderId) throws OrderException {
		List<Order> orders=orderRepo.getUsersOrders(orderId);
		
		return orders;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("PLACED");
		order.getPaymentDetails().setPaymentStatus("Completed");
		return order;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CONFIRMED");
		
		return orderRepo.save(order);
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("SHIPPED");
		
		return orderRepo.save(order);
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("DELIVERED");
		
		return orderRepo.save(order);
	}

	@Override
	public Order cancelledOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		order.setOrderStatus("CANCELLED");
		
		return orderRepo.save(order);
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		Optional<Order> opt=orderRepo.findById(orderId);
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new OrderException("Order does not exist with id-"+orderId);
	}

	@Override
	public List<Order> getAllOrders() throws OrderException {
		
		return orderRepo.findAll();
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		Order order=findOrderById(orderId);
		orderRepo.deleteById(orderId);
		
	}

}
