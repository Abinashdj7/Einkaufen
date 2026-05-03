package com.Abinash.Nouveauecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Abinash.Nouveauecommerce.Model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{
	
	@Query("SELECT c from Cart c WHERE c.user.id=:userId")
	public Cart findByUserId(@Param("userId") Long userId);
	
}
