package com.Abinash.Nouveauecommerce.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Category;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Repo.CategoryRepo;
import com.Abinash.Nouveauecommerce.Repo.ProductRepo;
import com.Abinash.Nouveauecommerce.Request.CreateProductRequest;

@ExtendWith(MockitoExtension.class)
class CustomerProductServiceImplementationTest {

    @Mock
    private ProductRepo productRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomerProductServiceImplentation service;

    @Test
    @DisplayName("findProductById - returns product when present")
    void findProductById_returnsProduct() throws Exception {
        Product product = new Product();
        product.setId(10L);
        product.setTitle("T");

        when(productRepo.findById(10L)).thenReturn(Optional.of(product));

        Product result = service.findProductById(10L);
        assertEquals("T", result.getTitle());
        verify(productRepo, times(1)).findById(10L);
    }

    @Test
    @DisplayName("findProductById - throws ProductException when not found")
    void findProductById_throwsWhenMissing() {
        when(productRepo.findById(999L)).thenReturn(Optional.empty());

        ProductException ex = assertThrows(ProductException.class, () -> service.findProductById(999L));
        assertEquals("Product not found with id-999", ex.getMessage());
        verify(productRepo, times(1)).findById(999L);
    }

    @Test
    @DisplayName("searchProduct - delegates to repo")
    void searchProduct_delegatesToRepo() {
        when(productRepo.searchProduct("abc")).thenReturn(Collections.emptyList());

        assertEquals(0, service.searchProduct("abc\u0000".replace("\u0000", "")).size());
        verify(productRepo, times(1)).searchProduct("abc");
    }

    @Test
    @DisplayName("deleteProduct - clears sizes and deletes")
    void deleteProduct_clearsSizesAndDeletes() throws Exception {
        Product product = new Product();
        product.setId(5L);
        product.setSizes(new java.util.HashSet<>(Collections.singleton(null)));

        when(productRepo.findById(5L)).thenReturn(Optional.of(product));

        String msg = service.deleteProduct(5L);
        assertEquals("Product deleted successfully", msg);
        verify(productRepo, times(1)).delete(product);
        assertEquals(0, product.getSizes().size());
    }

    @Test
    @DisplayName("getAllProduct - applies stock filtering")
    void getAllProduct_filtersStockInMemory() {
        Category category = new Category();

        Product inStock = new Product();
        inStock.setId(1L);
        inStock.setQuantity(2);
        inStock.setCategory(category);

        Product outOfStock = new Product();
        outOfStock.setId(2L);
        outOfStock.setQuantity(0);
        outOfStock.setCategory(category);

        when(productRepo.filterProducts(
                "cat",
                10,
                100,
                5,
                "relevance",
                "red"))
                .thenReturn(java.util.List.of(inStock, outOfStock));

        Page<Product> page = service.getAllProduct(
                "cat",
                "red",
                Collections.emptyList(),
                10,
                100,
                5,
                "relevance",
                "in_stock",
                0,
                10);

        assertEquals(1, page.getContent().size());
        assertEquals(1L, page.getContent().get(0).getId());
    }

    @Test
    @DisplayName("createProduct - creates new product with category hierarchy")
    void createProduct_createsProductWithCategories() {
        CreateProductRequest request = new CreateProductRequest();
        request.setTitle("Test Product");
        request.setDescription("Test Description");
        request.setPrice(100);
        request.setDiscountedPrice(80);
        request.setDiscountPercent(20);
        request.setColour("Blue");
        request.setBrand("TestBrand");
        request.setImageUrl("http://test.com/image.jpg");
        request.setQuantity(10);
        request.setTopLevelCategory("Electronics");
        request.setSecondLevelCategory("Phones");
        request.setThirdLevelCategory("Smartphones");

        Category topLevel = new Category();
        topLevel.setId(1L);
        topLevel.setName("Electronics");
        topLevel.setLevel(1);

        Category secondLevel = new Category();
        secondLevel.setId(2L);
        secondLevel.setName("Phones");
        secondLevel.setParentCategory(topLevel);
        secondLevel.setLevel(2);

        Category thirdLevel = new Category();
        thirdLevel.setId(3L);
        thirdLevel.setName("Smartphones");
        thirdLevel.setParentCategory(secondLevel);
        thirdLevel.setLevel(3);

        when(categoryRepo.findByName("Electronics")).thenReturn(topLevel);
        when(categoryRepo.findByNameAndParent("Phones", "Electronics")).thenReturn(secondLevel);
        when(categoryRepo.findByNameAndParent("Smartphones", "Phones")).thenReturn(thirdLevel);
        when(productRepo.save(any(Product.class))).thenAnswer(i -> {
            Product p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        Product result = service.createProduct(request);

        assertEquals("Test Product", result.getTitle());
        assertEquals("Blue", result.getColour());
        assertEquals(100, result.getPrice());
        verify(productRepo, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("updateProduct - updates quantity when greater than 0")
    void updateProduct_updatesQuantity() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Test Product");
        existingProduct.setQuantity(5);

        Product updateRequest = new Product();
        updateRequest.setQuantity(15);

        when(productRepo.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = service.updateProduct(1L, updateRequest);

        assertEquals(15, result.getQuantity());
        verify(productRepo, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("updateProduct - does not update quantity when 0")
    void updateProduct_doesNotUpdateWhenZero() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setTitle("Test Product");
        existingProduct.setQuantity(5);

        Product updateRequest = new Product();
        updateRequest.setQuantity(0);

        when(productRepo.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepo.save(any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product result = service.updateProduct(1L, updateRequest);

        assertEquals(5, result.getQuantity());
        verify(productRepo, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("findByProductByCategory - returns products for category")
    void findByProductByCategory_returnsProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        when(productRepo.findByCategory("Electronics")).thenReturn(java.util.List.of(product1, product2));

        java.util.List<Product> result = service.findByProductByCategory("Electronics");

        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getTitle());
        assertEquals("Product 2", result.get(1).getTitle());
        verify(productRepo, times(1)).findByCategory("Electronics");
    }

    @Test
    @DisplayName("findByProductByCategory - returns empty list when no products")
    void findByProductByCategory_returnsEmptyList() {
        when(productRepo.findByCategory("NonExistent")).thenReturn(Collections.emptyList());

        java.util.List<Product> result = service.findByProductByCategory("NonExistent");

        assertEquals(0, result.size());
        verify(productRepo, times(1)).findByCategory("NonExistent");
    }

    @Test
    @DisplayName("getAllProducts - returns all products")
    void getAllProducts_returnsAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setTitle("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setTitle("Product 2");

        when(productRepo.findAll()).thenReturn(java.util.List.of(product1, product2));

        java.util.List<Product> result = service.getAllProducts();

        assertEquals(2, result.size());
        verify(productRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("getAllProducts - returns empty list when no products exist")
    void getAllProducts_returnsEmpty() {
        when(productRepo.findAll()).thenReturn(Collections.emptyList());

        java.util.List<Product> result = service.getAllProducts();

        assertEquals(0, result.size());
        verify(productRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("recentlyAddedProduct - returns top 10 recently added products")
    void recentlyAddedProduct_returnTopTen() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCreatedAt(LocalDateTime.now());

        when(productRepo.findTop10ByOrderByCreatedAtDesc()).thenReturn(java.util.List.of(product1));

        java.util.List<Product> result = service.recentlyAddedProduct();

        assertEquals(1, result.size());
        verify(productRepo, times(1)).findTop10ByOrderByCreatedAtDesc();
    }

    @Test
    @DisplayName("getProductTrue - returns products by category hierarchy")
    void getProductTrue_returnsByCategoryHierarchy() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Product in category");

        when(productRepo.getProductsByCategory("Level1", "Level2", "Level3"))
                .thenReturn(java.util.List.of(product));

        java.util.List<Product> result = service.getProductTrue("Level1", "Level2", "Level3");

        assertEquals(1, result.size());
        assertEquals("Product in category", result.get(0).getTitle());
        verify(productRepo, times(1)).getProductsByCategory("Level1", "Level2", "Level3");
    }

    @Test
    @DisplayName("getProductTrue - returns empty list when no matching categories")
    void getProductTrue_returnsEmptyForNonExistentCategory() {
        when(productRepo.getProductsByCategory("Fake1", "Fake2", "Fake3"))
                .thenReturn(Collections.emptyList());

        java.util.List<Product> result = service.getProductTrue("Fake1", "Fake2", "Fake3");

        assertEquals(0, result.size());
        verify(productRepo, times(1)).getProductsByCategory("Fake1", "Fake2", "Fake3");
    }
}
