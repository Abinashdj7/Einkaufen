package com.Abinash.Nouveauecommerce.Integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.Abinash.Nouveauecommerce.Model.Category;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Repo.CategoryRepo;
import com.Abinash.Nouveauecommerce.Repo.ProductRepo;

/**
 * Exercises product browsing/search against a real (H2) database:
 * Controller -> Service -> Repository -> JPA, with no mocked layers.
 * Data is seeded directly through the repositories so the assertions
 * verify behaviour against persisted rows rather than stubbed values.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    private Product seededShoe;
    private Product seededShirt;

    @BeforeEach
    void seedProducts() {
        Category electronics = categoryRepo.save(category("Electronics", null, 1));
        Category clothing = categoryRepo.save(category("Clothing", null, 1));

        seededShoe = productRepo.save(product("Running Shoe", "Lightweight running shoe",
                3000, 2400, 20, "SpeedStep", "white", 25, clothing));
        seededShirt = productRepo.save(product("Cotton Shirt", "Casual cotton shirt",
                1200, 1000, 17, "FashionBrand", "blue", 0, clothing));
        productRepo.save(product("Wireless Mouse", "Ergonomic wireless mouse",
                1500, 1500, 0, "TechAudio", "black", 40, electronics));
    }

    private Category category(String name, Category parent, int level) {
        Category category = new Category();
        category.setName(name);
        category.setParentCategory(parent);
        category.setLevel(level);
        return category;
    }

    private Product product(String title, String description, int price, int discountedPrice,
            int discountPercent, String brand, String colour, int quantity, Category category) {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setDiscountedPrice(discountedPrice);
        product.setDiscountPercent(discountPercent);
        product.setBrand(brand);
        product.setColour(colour);
        product.setQuantity(quantity);
        product.setCategory(category);
        return product;
    }

    @Test
    @DisplayName("GET /api/allProducts returns every persisted product")
    void everyProduct_returnsAllPersistedProducts() throws Exception {
        mockMvc.perform(get("/api/allProducts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[*].title").value(org.hamcrest.Matchers.containsInAnyOrder(
                        "Running Shoe", "Cotton Shirt", "Wireless Mouse")));
    }

    @Test
    @DisplayName("GET /api/products/id/{id} returns the persisted product by its real id")
    void findProductById_returnsPersistedProduct() throws Exception {
        mockMvc.perform(get("/api/products/id/" + seededShoe.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(seededShoe.getId()))
                .andExpect(jsonPath("$.title").value("Running Shoe"))
                .andExpect(jsonPath("$.brand").value("SpeedStep"));
    }

    @Test
    @DisplayName("GET /api/products/id/{id} returns 404 for an id that does not exist")
    void findProductById_returnsNotFoundForMissingProduct() throws Exception {
        long missingId = seededShoe.getId() + seededShirt.getId() + 9999;

        mockMvc.perform(get("/api/products/id/" + missingId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/product/search matches persisted products by title")
    void searchProduct_matchesPersistedProductsByTitle() throws Exception {
        mockMvc.perform(get("/api/product/search").param("q", "Shoe").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Running Shoe"));
    }

    @Test
    @DisplayName("GET /api/product/search returns an empty list when nothing matches")
    void searchProduct_returnsEmptyListWhenNoMatch() throws Exception {
        mockMvc.perform(get("/api/product/search").param("q", "NoSuchProductXYZ").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @DisplayName("GET /api/products filters by category and sorts by ascending price")
    void getAllProduct_filtersByCategoryAndSortsByPrice() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("category", "Clothing")
                .param("color", "")
                .param("minPrice", "0")
                .param("maxPrice", "100000")
                .param("minDiscount", "0")
                .param("sort", "price_low")
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("Cotton Shirt"))
                .andExpect(jsonPath("$.content[1].title").value("Running Shoe"));
    }

    @Test
    @DisplayName("GET /api/products excludes products outside the requested price range")
    void getAllProduct_excludesProductsOutsidePriceRange() throws Exception {
        mockMvc.perform(get("/api/products")
                .param("category", "Clothing")
                .param("color", "")
                .param("minPrice", "2000")
                .param("maxPrice", "3000")
                .param("minDiscount", "0")
                .param("sort", "price_low")
                .param("pageNumber", "0")
                .param("pageSize", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Running Shoe"));
    }
}
