package com.Abinash.Nouveauecommerce.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Abinash.Nouveauecommerce.Model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{

	@Query("SELECT p FROM Product p "
			+ "WHERE (p.category.name=:category OR :category='' OR p.colour=:colour) "
			+ "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR"
			+ " (p.discountedPrice BETWEEN :minPrice AND :maxPrice)) "
			+ "AND(:minDiscount IS NULL OR p.discountPercent>=:minDiscount) "
			+ "ORDER BY "
			+ "CASE WHEN :sort ='price_low' THEN p.discountedPrice END ASC,"
			+ "CASE WHEN :sort ='price_low' THEN p.discountedPrice END DESC")
	public List<Product> filterProducts(@Param("category") String category,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice,
			@Param("minDiscount") Integer minDiscount,
			@Param("sort") String sort,
			@Param("colour") String colour);
	
	@Query("SELECT p From Product p Where LOWER(p.category.name)=:category")
	public List<Product> findByCategory(@Param("category") String category);
	
	@Query("SELECT p From Product p where LOWER(p.title) Like %:query% OR LOWER(p.description) Like %:query% OR LOWER(p.brand) LIKE %:query% OR LOWER(p.category.name) LIKE %:query%")
	public List<Product> searchProduct(@Param("query")String query);

	public List<Product> findTop10ByOrderByCreatedAtDesc();
	
	@Query("SELECT p FROM Product p "
		       + "JOIN p.category c3 "
		       + "JOIN c3.parentCategory c2 "
		       + "JOIN c2.parentCategory c1 "
		       + "WHERE c1.name = :levelOneCategory "
		       + "AND c2.name = :levelTwoCategory "
		       + "AND c3.name = :levelThreeCategory")
	public List<Product> getProductsByCategory(@Param("levelOneCategory") String levelOneCategory,@Param("levelTwoCategory") String levelTwoCategory,@Param("levelThreeCategory") String levelThreeCategory);
}
