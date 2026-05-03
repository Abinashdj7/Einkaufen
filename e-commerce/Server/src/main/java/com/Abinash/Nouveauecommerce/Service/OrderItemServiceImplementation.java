package com.Abinash.Nouveauecommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Model.OrderItem;
import com.Abinash.Nouveauecommerce.Repo.OrderItemRepo;

@Service
public class OrderItemServiceImplementation implements OrderItemService {

	
	@Autowired
	OrderItemRepo orderItemRepo;
	
	@Override
	public OrderItem createOrderItem(OrderItem orderItem) {
		// TODO Auto-generated method stub
		return orderItemRepo.save(orderItem);
	}

}
