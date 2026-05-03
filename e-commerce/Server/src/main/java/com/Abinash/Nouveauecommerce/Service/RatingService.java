package com.Abinash.Nouveauecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Rating;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Request.RatingRequest;

@Service
public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user) throws ProductException;
	
	public List<Rating> getProductsRating(Long productId);
	
	
}
