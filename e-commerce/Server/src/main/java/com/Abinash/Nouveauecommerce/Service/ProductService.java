package com.Abinash.Nouveauecommerce.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Request.CreateProductRequest;

@Service
public interface ProductService {
	
	public Product createProduct(CreateProductRequest req);
	
	public String deleteProduct(Long productId) throws ProductException;
	
	public Product updateProduct(Long productId,Product req) throws ProductException;
	
	public Product findProductById(Long id) throws ProductException;
	
	public List<Product> findByProductByCategory(String category);
	
	public Page<Product> getAllProduct(String category,String color
			,List<String> sizes,Integer minPrice,Integer maxPrice,
			Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
	
	public List<Product> getAllProducts();
	
	public List<Product> recentlyAddedProduct();
	
	public List<Product> searchProduct(String query);
	
	public List<Product> getProductTrue(String levelOneCategory,String levelTwoCategory, String levelThreeCategory);
}
