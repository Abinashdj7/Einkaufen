package com.Abinash.Nouveauecommerce.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Review;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Request.ReviewRequest;

@Service
public interface ReviewService {
	
	public Review createReview(ReviewRequest req,User user) throws ProductException;
	
	public List<Review> getAllReviews(Long productId);
}
