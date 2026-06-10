package com.Abinash.Nouveauecommerce.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.Abinash.Nouveauecommerce.Config.JwtValidator;
import com.Abinash.Nouveauecommerce.Exception.ProductException;
import com.Abinash.Nouveauecommerce.Model.Category;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Service.ProductService;
import com.Abinash.testsupport.TestConfig;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@WebMvcTest(controllers = ProductController.class,
                excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtValidator.class))
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import(TestConfig.class)
class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductService productService;

        @Test
        @DisplayName("GET /api/products/id/{productId} returns product")
        void findProductByIdHandler_returnsProduct() throws Exception {
                Product product = new Product();
                product.setId(1L);
                product.setTitle("Test Product");
                product.setCategory(new Category());
                when(productService.findProductById(eq(1L))).thenReturn(product);

                mockMvc.perform(get("/api/products/id/1").accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.title").value("Test Product"));

                verify(productService).findProductById(1L);
        }

        @Test
        @DisplayName("GET /api/allProducts returns list")
        void everyProduct_returnsList() throws Exception {
                Product product = new Product();
                product.setId(1L);
                product.setTitle("P1");
                List<Product> products = List.of(product);
                when(productService.getAllProducts()).thenReturn(products);

                mockMvc.perform(get("/api/allProducts").accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].title").value("P1"));

                verify(productService).getAllProducts();
        }

        @Test
        @DisplayName("GET /api/product/search?q=... returns list")
        void searchProductHandler_returnsList() throws Exception {
                Product product = new Product();
                product.setId(2L);
                product.setTitle("Found");
                List<Product> products = Collections.singletonList(product);
                when(productService.searchProduct(eq("shoe"))).thenReturn(products);

                mockMvc.perform(get("/api/product/search").param("q", "shoe").accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(2))
                                .andExpect(jsonPath("$[0].title").value("Found"));

                verify(productService).searchProduct("shoe");
        }

        @Test
        @DisplayName("GET /api/products paged endpoint returns page")
        void findProductByCategoryHandler_returnsPage() throws Exception {
                Product product = new Product();
                product.setId(3L);
                product.setTitle("Category Product");

                Page<Product> page = new PageImpl<>(List.of(product), PageRequest.of(0, 10), 1);
                when(productService.getAllProduct(
                                eq("cat"),
                                eq("red"),
                                any(),
                                eq(10),
                                eq(100),
                                eq(5),
                                eq("relevance"),
                                eq(null),
                                eq(0),
                                eq(10)))
                                .thenReturn(page);

                mockMvc.perform(
                                get("/api/products")
                                                .param("category", "cat")
                                                .param("color", "red")
                                                .param("sizes", "S", "M")
                                                .param("minPrice", "10")
                                                .param("maxPrice", "100")
                                                .param("minDiscount", "5")
                                                .param("sort", "relevance")
                                                .param("pageNumber", "0")
                                                .param("pageSize", "10")
                                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$.content[0].id").value(3))
                                .andExpect(jsonPath("$.content[0].title").value("Category Product"));
        }
}
