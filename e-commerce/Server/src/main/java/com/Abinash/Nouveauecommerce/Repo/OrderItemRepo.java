package com.Abinash.Nouveauecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Abinash.Nouveauecommerce.Model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
	
}
