package com.Abinash.Nouveauecommerce.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Service.ProductService;

@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
			@RequestParam String color, List<String> sizes, @RequestParam Integer minPrice,
			@RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
			@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
		logger.info("PRODUCT CONTROLLER: Received request to find products by category - " + category);
		Page<Product> res = productService.getAllProduct(category, color, sizes, minPrice, maxPrice, minDiscount, sort,
				null, pageNumber, pageSize);
		logger.info("PRODUCT CONTROLLER: Found " + res.getContent().size() + " products for category " + category);
		return new ResponseEntity<Page<Product>>(res, HttpStatus.ACCEPTED);
	}

	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
		logger.info("PRODUCT CONTROLLER: Fetching product by ID - " + productId);
		Product product = productService.findProductById(productId);
		logger.info("PRODUCT CONTROLLER: Found product - " + product.getTitle());
		return new ResponseEntity<Product>(product, HttpStatus.ACCEPTED);
	}

	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q) {
		logger.info("PRODUCT CONTROLLER: Searching products with query - " + q);
		List<Product> products = productService.searchProduct(q);
		logger.info("PRODUCT CONTROLLER: Found " + products.size() + " products matching query: " + q);
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/allProducts")
	public ResponseEntity<List<Product>> everyProduct() {
		logger.info("PRODUCT CONTROLLER: Fetching all products from database");
		List<Product> products = productService.getAllProducts();
		logger.info("PRODUCT CONTROLLER: Retrieved " + products.size() + " products from database");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/category/{levelOneCategory}/{levelTwoCategory}/{levelThreeCategory}")
	public ResponseEntity<List<Product>> getProductTrue(@PathVariable String levelOneCategory,
			@PathVariable String levelTwoCategory,
			@PathVariable String levelThreeCategory) {
		logger.info("PRODUCT CONTROLLER: Fetching products for category path - " + levelOneCategory + "/"
				+ levelTwoCategory + "/" + levelThreeCategory);
		List<Product> products = productService.getProductTrue(levelOneCategory, levelTwoCategory, levelThreeCategory);
		logger.info("PRODUCT CONTROLLER: Found " + products.size() + " products for category path");
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@GetMapping("/debug/products/count")
	public ResponseEntity<String> getProductCount() {
		logger.info("PRODUCT CONTROLLER: Debug endpoint called - getting product count");
		List<Product> products = productService.getAllProducts();
		String message = "Total products in database: " + products.size();
		logger.info("PRODUCT CONTROLLER: " + message);

		products.stream()
				.filter(p -> p.getCategory() != null)
				.collect(java.util.stream.Collectors.groupingBy(p -> p.getCategory().getName()))
				.forEach((category, productList) -> logger
						.info("  Category [" + category + "]: " + productList.size() + " products"));

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
