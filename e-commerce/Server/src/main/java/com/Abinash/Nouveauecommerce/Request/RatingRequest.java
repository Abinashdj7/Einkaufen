package com.Abinash.Nouveauecommerce.Request;

public class RatingRequest {
	private long productId;
	
	private double rating;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
}
