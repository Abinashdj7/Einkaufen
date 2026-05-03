package com.Abinash.Nouveauecommerce.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.CartItem;
import com.Abinash.Nouveauecommerce.Model.Product;

public interface CartItemRepo extends JpaRepository<CartItem, Long>{

	@Query("SELECT ci FROM CartItem ci "
			+ "WHERE ci.cart=:cart AND ci.product=:product AND ci.size=:size"
			+ " AND ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart,@Param("product") Product product,
			@Param("size") String size,@Param("userId") Long userId);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.userId=:userId")
	public void clearCart(@Param("userId") Long userId);
}
