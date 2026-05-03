package com.Abinash.Nouveauecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import com.Abinash.Nouveauecommerce.Model.Rating;

public interface RatingRepo extends JpaRepository<Rating, Long>{
	
	@Query("SELECT r FROM Rating r WHERE r.product.id=:productId")
	public List<Rating> getAllProductsRating(@Param("productId") Long productId);
}
