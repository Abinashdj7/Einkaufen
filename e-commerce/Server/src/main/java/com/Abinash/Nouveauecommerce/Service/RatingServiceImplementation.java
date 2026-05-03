package com.Abinash.Nouveauecommerce.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Model.Rating;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.RatingRepo;
import com.Abinash.Nouveauecommerce.Request.RatingRequest;

@Service
public class RatingServiceImplementation implements RatingService{

	
	private RatingRepo ratingRepo;
	
	private ProductService productService;
	
	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product=productService.findProductById(req.getProductId());
		Rating rating=new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		
		return ratingRepo.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {
		return ratingRepo.getAllProductsRating(productId);
	}
	
}
