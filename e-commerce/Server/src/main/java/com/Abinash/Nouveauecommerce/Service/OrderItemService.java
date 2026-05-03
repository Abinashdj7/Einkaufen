package com.Abinash.Nouveauecommerce.Service;

import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Model.OrderItem;

@Service
public interface OrderItemService {

	public OrderItem createOrderItem(OrderItem orderItem);
}
