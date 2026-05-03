package com.Abinash.Nouveauecommerce.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Model.Review;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.ProductRepo;
import com.Abinash.Nouveauecommerce.Repo.ReviewRepo;
import com.Abinash.Nouveauecommerce.Request.ReviewRequest;

@Service
public class ReviewServiceImplementation implements ReviewService {

	
	private ReviewRepo reviewRepo;
	
	private ProductService productService;
	
	
	private ProductRepo productRepo;
	
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {
		Product product=productService.findProductById(req.getProductId());
		
		Review review=new Review();
		
		review.setUser(user);
		review.setProduct(product);
		review.setReview(req.getReview());
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepo.save(review);
	}

	@Override
	public List<Review> getAllReviews(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepo.getAllProductsReview(productId);
	}
	
}
