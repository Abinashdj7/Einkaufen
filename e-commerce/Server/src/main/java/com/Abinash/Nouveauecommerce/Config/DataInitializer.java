package com.Abinash.Nouveauecommerce.Config;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Abinash.Nouveauecommerce.Model.Category;
import com.Abinash.Nouveauecommerce.Model.Product;
import com.Abinash.Nouveauecommerce.Model.Size;
import com.Abinash.Nouveauecommerce.Repo.CategoryRepo;
import com.Abinash.Nouveauecommerce.Repo.ProductRepo;

@Configuration
public class DataInitializer {

        private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

        @Bean
        CommandLineRunner initDatabase(ProductRepo productRepo, CategoryRepo categoryRepo) {
                return args -> {
                        logger.info("========================================");
                        logger.info("DATA INITIALIZER: Starting initialization");
                        logger.info("========================================");

                        // Check if data already exists - but still create categories and new custom
                        // products
                        boolean isFirstRun = productRepo.count() == 0;
                        if (isFirstRun) {
                                logger.info("DATA INITIALIZER: Database is empty. Starting full sample data initialization...");
                                System.out.println("Initializing database with sample data...");
                        } else {
                                logger.warn("DATA INITIALIZER: Database already contains " + productRepo.count()
                                                + " products. Still ensuring categories exist...");
                                System.out.println(
                                                "Database already initialized, ensuring categories and custom products exist...");
                        }

                        // Create Level 1 parent categories (top-level)
                        Category mens = getOrCreateCategory("mens", 1, null, categoryRepo);
                        Category womens = getOrCreateCategory("womens", 1, null, categoryRepo);
                        Category electronics = getOrCreateCategory("electronics", 1, null, categoryRepo);
                        Category home = getOrCreateCategory("home", 1, null, categoryRepo);
                        Category books = getOrCreateCategory("books", 1, null, categoryRepo);

                        // Create Level 2 sub-categories (clothing, electronics, etc.)
                        Category mensClothing = createSubCategory("clothing", "Clothing", mens, categoryRepo);
                        Category womensClothing = createSubCategory("clothing", "Clothing", womens, categoryRepo);

                        // Create Men's Level 3 sub-categories
                        Category mensShirt = createSubCategory("mens_shirt", "Shirt", mensClothing, categoryRepo);
                        Category mensJeans = createSubCategory("mens_jeans", "Jeans", mensClothing, categoryRepo);
                        Category mensSweater = createSubCategory("mens_sweater", "Sweaters", mensClothing,
                                        categoryRepo);
                        Category mensTShirt = createSubCategory("mens_t-shirt", "T-Shirts", mensClothing, categoryRepo);
                        Category mensJacket = createSubCategory("mens_jacket", "Jackets", mensClothing, categoryRepo);
                        Category mensActivewear = createSubCategory("mens_activewear", "Activewear", mensClothing,
                                        categoryRepo);
                        Category mensKurta = createSubCategory("mens_kurta", "Kurtas", mensClothing, categoryRepo);

                        // Create NEW Men's Level 3 sub-categories (User's custom categories)
                        Category mensTrouser = createSubCategory("mens_trouser", "Trousers", mensClothing,
                                        categoryRepo);
                        Category mensCoat = createSubCategory("mens_coat", "Coats", mensClothing, categoryRepo);
                        Category mensShoes = createSubCategory("mens_shoes", "Shoes", mensClothing, categoryRepo);
                        Category mensSocks = createSubCategory("mens_socks", "Socks", mensClothing, categoryRepo);
                        Category mensBelt = createSubCategory("mens_belt", "Belts", mensClothing, categoryRepo);

                        // Create Women's Level 3 sub-categories
                        Category womensTop = createSubCategory("womens_top", "Tops", womensClothing, categoryRepo);
                        Category womensDress = createSubCategory("womens_dress", "Dresses", womensClothing,
                                        categoryRepo);
                        Category womensJeans = createSubCategory("womens_jeans", "Women Jeans", womensClothing,
                                        categoryRepo);
                        Category womensSweater = createSubCategory("womens_sweater", "Sweaters", womensClothing,
                                        categoryRepo);
                        Category womensTShirt = createSubCategory("womens_t-shirt", "T-Shirts", womensClothing,
                                        categoryRepo);
                        Category womensJacket = createSubCategory("womens_jacket", "Jackets", womensClothing,
                                        categoryRepo);
                        Category womensSaree = createSubCategory("womens_saree", "Sarees", womensClothing,
                                        categoryRepo);
                        Category womensKurtas = createSubCategory("womens_kurtas", "Kurtas", womensClothing,
                                        categoryRepo);

                        // ==================== ELECTRONICS (5 products) ====================
                        List<Product> products = Arrays.asList(
                                        // Product 1: Wireless Headphones
                                        createProduct("Wireless Bluetooth Headphones",
                                                        "Premium over-ear wireless headphones with active noise cancellation, 30-hour battery life, and premium sound quality",
                                                        2999, 1499, 50, 100, "SoundMax", "Black",
                                                        createSizes("S", 20, "M", 30, "L", 25),
                                                        "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500",
                                                        electronics, 4, 1250),

                                        // Product 2: Smart Watch
                                        createProduct("Smart Watch Pro",
                                                        "Advanced fitness tracker with heart rate monitoring, GPS tracking, SpO2 sensor, and 7-day battery life",
                                                        4999, 3499, 30, 50, "TechLife", "Silver",
                                                        createSizes("One Size", 50),
                                                        "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500",
                                                        electronics, 5, 2100),

                                        // Product 3: Wireless Mouse
                                        createProduct("Ergonomic Wireless Mouse",
                                                        "Precision wireless mouse with ergonomic design, 16000 DPI sensor, and 70-hour battery life",
                                                        799, 499, 40, 150, "TechAccess", "Black",
                                                        createSizes("One Size", 150),
                                                        "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500",
                                                        electronics, 4, 320),

                                        // Product 4: Tablet
                                        createProduct("Tablet Pro 10.1\"",
                                                        "10.1 inch Full HD display, 4GB RAM, 64GB storage, octa-core processor, perfect for work and entertainment",
                                                        15999, 9999, 40, 30, "TechTab", "Gold",
                                                        createSizes("64GB", 15, "128GB", 15),
                                                        "https://images.unsplash.com/photo-1542751110-97427fffecb9?w=500",
                                                        electronics, 4, 890),

                                        // Product 5: Bluetooth Speaker
                                        createProduct("Portable Bluetooth Speaker",
                                                        "Waterproof portable speaker with 360° surround sound, 20-hour playtime, and built-in microphone",
                                                        2499, 1299, 50, 80, "SoundBoost", "Blue",
                                                        createSizes("One Size", 80),
                                                        "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500",
                                                        electronics, 4, 560),

                                        // ==================== MEN'S CLOTHING (5 products) ====================
                                        // Product 1: Classic Cotton Shirt -> Shirt category
                                        createProduct("Men's Classic Cotton Shirt",
                                                        "Premium 100% cotton casual shirt for everyday comfort, breathable fabric with perfect fit",
                                                        1299, 799, 40, 200, "StyleHub", "White",
                                                        createSizes("S", 50, "M", 75, "L", 50, "XL", 25),
                                                        "https://images.unsplash.com/photo-1596755094514-f87e34085b2e?w=500",
                                                        mensShirt, 4, 650),

                                        // Product 2: Running Shoes -> Activewear category
                                        createProduct("Men's Running Shoes",
                                                        "Lightweight breathable running shoes with cushioned sole, anti-slip grip, and superior comfort",
                                                        3499, 2499, 30, 80, "SportX", "Black",
                                                        createSizes("US7", 20, "US8", 25, "US9", 20, "US10", 15),
                                                        "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500",
                                                        mensActivewear, 5, 1200),

                                        // Product 3: Denim Jeans -> Jeans category
                                        createProduct("Men's Slim Fit Denim Jeans",
                                                        "Premium stretch denim jeans with modern slim fit, comfortable waistband, and classic 5-pocket style",
                                                        1999, 1299, 35, 120, "DenimCraft", "Navy",
                                                        createSizes("28", 20, "30", 30, "32", 40, "34", 20, "36", 10),
                                                        "https://images.unsplash.com/photo-1542272604-787c3835535d?w=500",
                                                        mensJeans, 4, 480),

                                        // Product 4: Wool Sweater -> Sweaters category
                                        createProduct("Men's Wool Blend Sweater",
                                                        "Warm and cozy wool blend sweater, perfect for winter season, classic crew neck design",
                                                        2199, 1499, 32, 60, "WinterWear", "Grey",
                                                        createSizes("S", 15, "M", 20, "L", 15, "XL", 10),
                                                        "https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=500",
                                                        mensSweater, 4, 380),

                                        // Product 5: Leather Jacket -> Jackets category
                                        createProduct("Men's Genuine Leather Jacket",
                                                        "Premium genuine leather biker jacket, modern fit, anti-zipper closure, timeless style",
                                                        5999, 3999, 33, 25, "LeatherCraft", "Brown",
                                                        createSizes("S", 5, "M", 8, "L", 7, "XL", 5),
                                                        "https://images.unsplash.com/photo-1551028719-00167b16acac?w=500",
                                                        mensJacket, 4, 850),

                                        // ==================== MEN'S NEW CUSTOM CATEGORIES ====================
                                        // Product 6: Men's T-Shirt -> T-Shirts category
                                        createProduct("Men's Cotton Round Neck T-Shirt",
                                                        "Premium 100% cotton casual t-shirt, comfortable fit, perfect for everyday wear",
                                                        899, 499, 45, 150, "CasualHub", "White",
                                                        createSizes("S", 40, "M", 60, "L", 40, "XL", 30),
                                                        "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500",
                                                        mensTShirt, 4, 320),

                                        // Product 7: Men's Trouser -> Trousers category
                                        createProduct("Men's Formal Trousers",
                                                        "Premium formal trousers, slim fit, perfect for office wear and formal occasions",
                                                        1999, 1299, 35, 80, "FormalWear", "Black",
                                                        createSizes("28", 15, "30", 25, "32", 30, "34", 15, "36", 10),
                                                        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=500",
                                                        mensTrouser, 4, 280),

                                        // Product 8: Men's Coat -> Coats category
                                        createProduct("Men's Wool Winter Coat",
                                                        "Premium wool blend winter coat, classic design, warm and stylish for winter season",
                                                        4999, 3499, 30, 40, "WinterStyle", "Navy",
                                                        createSizes("S", 10, "M", 15, "L", 10, "XL", 5),
                                                        "https://images.unsplash.com/photo-1539533018447-63fcce2678e3?w=500",
                                                        mensCoat, 4, 450),

                                        // Product 9: Men's Shoes -> Shoes category
                                        createProduct("Men's Classic Leather Shoes",
                                                        "Premium leather formal shoes, shiny finish, comfortable sole for office wear",
                                                        2999, 1999, 33, 50, "LeatherSole", "Black",
                                                        createSizes("US7", 10, "US8", 15, "US9", 15, "US10", 10),
                                                        "https://images.unsplash.com/photo-1614252369475-992a32e3604d?w=500",
                                                        mensShoes, 4, 380),

                                        // Product 10: Men's Socks -> Socks category
                                        createProduct("Men's Cotton Athletic Socks",
                                                        "Premium cotton athletic socks, breathable fabric, pack of 3 pairs",
                                                        499, 299, 40, 200, "ComfortFit", "White",
                                                        createSizes("One Size", 200),
                                                        "https://images.unsplash.com/Photo-1581858726788-75fb0a0fc5b1?w=500",
                                                        mensSocks, 4, 180),

                                        // Product 11: Men's Belt -> Belts category
                                        createProduct("Men's Leather Dress Belt",
                                                        "Premium genuine leather dress belt, classic silver buckle, formal wear essential",
                                                        799, 499, 38, 100, "LeatherCraft", "Black",
                                                        createSizes("S", 20, "M", 30, "L", 30, "XL", 20),
                                                        "https://images.unsplash.com/photo-1624222247344-550fb60583dc?w=500",
                                                        mensBelt, 4, 220),

                                        // ==================== WOMEN'S CLOTHING (5 products) ====================
                                        // Product 1: Summer Dress -> Dresses category
                                        createProduct("Women's Summer Floral Dress",
                                                        "Elegant floral print summer dress, lightweight fabric, perfect for casual and party occasions",
                                                        1999, 999, 50, 150, "FashionOne", "Blue",
                                                        createSizes("S", 30, "M", 60, "L", 40, "XL", 20),
                                                        "https://images.unsplash.com/photo-1572804013309-59a88b7e92f1?w=500",
                                                        womensDress, 4, 800),

                                        // Product 2: Women's Jeans -> Women Jeans category
                                        createProduct("Women's High Waist Jeans",
                                                        "High waist skinny fit jeans with stretch fabric, comfortable fit, trendy look",
                                                        1799, 999, 45, 100, "DenimVogue", "Light Blue",
                                                        createSizes("24", 15, "26", 25, "28", 30, "30", 20, "32", 10),
                                                        "https://images.unsplash.com/photo-1584370848010-d7cc63aefaa5?w=500",
                                                        womensJeans, 4, 420),

                                        // Product 3: Women's Sweater -> Sweaters category
                                        createProduct("Women's Cozy Wool Sweater",
                                                        "Soft and warm wool sweater, cable knit pattern, relaxed fit, perfect for winter",
                                                        1899, 1199, 37, 50, "WinterShe", "Pink",
                                                        createSizes("S", 15, "M", 20, "L", 10, "XL", 5),
                                                        "https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=500",
                                                        womensSweater, 4, 350),

                                        // Product 4: Silk Saree -> Sarees category
                                        createProduct("Women's Silk Saree",
                                                        "Pure silk ethnic saree with elegant zari work, perfect for weddings and festivals",
                                                        4999, 2999, 40, 30, "EthnicRoyal", "Red",
                                                        createSizes("One Size", 30),
                                                        "https://images.unsplash.com/photo-1610036782868-7dc2a6ec4d8d?w=500",
                                                        womensSaree, 5, 650),

                                        // Product 5: Anarkali Kurti -> Kurtas category
                                        createProduct("Women's Anarkali Kurti",
                                                        "Traditional Anarkali kurti with embroidery, flowy silhouette, party wear",
                                                        2499, 1499, 40, 40, "EthnicElegance", "Maroon",
                                                        createSizes("S", 10, "M", 15, "L", 10, "XL", 5),
                                                        "https://images.unsplash.com/photo-1583744946564-b52ac1c389c8?w=500",
                                                        womensKurtas, 4, 520),

                                        // ==================== HOME & LIVING (5 products) ====================
                                        // Product 1: Table Lamp
                                        createProduct("Modern LED Table Lamp",
                                                        "Modern LED table lamp with touch dimmer, 3 color temperatures, wireless charging base",
                                                        1599, 999, 40, 60, "HomeLight", "White",
                                                        createSizes("One Size", 60),
                                                        "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=500",
                                                        home, 4, 650),

                                        // Product 2: Coffee Maker
                                        createProduct("Automatic Coffee Maker",
                                                        "Automatic coffee maker with timer function, 1.5L capacity, quick brew technology",
                                                        2999, 1999, 35, 40, "BrewMaster", "Black",
                                                        createSizes("One Size", 40),
                                                        "https://images.unsplash.com/photo-1495474472287-4d71bcdd05e9?w=500",
                                                        home, 5, 850),

                                        // Product 3: LED TV
                                        createProduct("Smart LED TV 43\"",
                                                        "43 inch Full HD Smart LED TV, built-in Chromecast, Dolby audio, multiple HDMI ports",
                                                        24999, 19999, 20, 15, "HomeVision", "Black",
                                                        createSizes("One Size", 15),
                                                        "https://images.unsplash.com/photo-1593359677879-a4bb92f699d4?w=500",
                                                        home, 4, 1500),

                                        // Product 4: Air Purifier
                                        createProduct("Smart Air Purifier Pro",
                                                        "HEPA air purifier with real-time air quality monitor, 500 sq ft coverage, quiet operation",
                                                        8999, 5999, 33, 25, "PureAir", "White",
                                                        createSizes("One Size", 25),
                                                        "https://images.unsplash.com/photo-1585771724684-38269d3099c0?w=500",
                                                        home, 4, 720),

                                        // Product 5: Bedsheet Set
                                        createProduct("Cotton Bedsheet Set",
                                                        "100% cotton bedsheet set with 2 pillow covers, 300 thread count, fade resistant",
                                                        1999, 999, 50, 80, "SleepWell", "Light Blue",
                                                        createSizes("Single", 20, "Double", 40, "King", 20),
                                                        "https://images.unsplash.com/photo-1522771739844-6a9f6d5f53af?w=500",
                                                        home, 4, 380),

                                        // ==================== BOOKS (5 products) ====================
                                        // Product 1: Python Programming
                                        createProduct("Python Programming",
                                                        "Complete guide to Python programming for beginners, step-by-step learning with examples",
                                                        899, 599, 35, 500, "TechBooks", "N/A",
                                                        createSizes("One Size", 500),
                                                        "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=500",
                                                        books, 5, 350),

                                        // Product 2: Java Complete Reference
                                        createProduct("Java Complete Reference",
                                                        "Comprehensive Java programming guide covering core concepts to advanced topics",
                                                        1099, 749, 32, 300, "TechBooks", "N/A",
                                                        createSizes("One Size", 300),
                                                        "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=500",
                                                        books, 4, 280),

                                        // Product 3: Data Structures & Algorithms
                                        createProduct("Data Structures & Algorithms",
                                                        "Master data structures and algorithms with practical implementations and coding interview prep",
                                                        1199, 799, 33, 250, "Code Academy", "N/A",
                                                        createSizes("One Size", 250),
                                                        "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?w=500",
                                                        books, 4, 420),

                                        // Product 4: Web Development Guide
                                        createProduct("Modern Web Development",
                                                        "Full stack web development guide using HTML, CSS, JavaScript, React, and Node.js",
                                                        1299, 899, 31, 200, "WebAcademy", "N/A",
                                                        createSizes("One Size", 200),
                                                        "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=500",
                                                        books, 4, 380),

                                        // Product 5: Machine Learning Basics
                                        createProduct("Machine Learning Basics",
                                                        "Introduction to machine learning concepts, Python libraries, and practical projects",
                                                        1499, 999, 33, 150, "AIBooks", "N/A",
                                                        createSizes("One Size", 150),
                                                        "https://images.unsplash.com/photo-1555949963-aa79dcee981c?w=500",
                                                        books, 5, 520));

                        productRepo.saveAll(products);
                        System.out.println("Database initialized with " + products.size()
                                        + " sample products across 5 categories!");
                };
        }

        private Product createProduct(String title, String description, int price, int discountedPrice,
                        int discountPercent, int quantity, String brand, String colour, Set<Size> sizes,
                        String imageUrl, Category category, int numRatings, int numRatingsValue) {
                Product product = new Product();
                product.setTitle(title);
                product.setDescription(description);
                product.setPrice(price);
                product.setDiscountedPrice(discountedPrice);
                product.setDiscountPercent(discountPercent);
                product.setQuantity(quantity);
                product.setBrand(brand);
                product.setColour(colour);
                product.setSizes(sizes);
                product.setImageUrl(imageUrl);
                product.setCategory(category);
                product.setNumRatings(numRatings);
                product.setCreatedAt(LocalDateTime.now());
                return product;
        }

        private Set<Size> createSizes(Object... nameAndQuantity) {
                Set<Size> sizes = new HashSet<>();
                for (int i = 0; i < nameAndQuantity.length; i += 2) {
                        Size size = new Size(String.valueOf(nameAndQuantity[i]),
                                        Integer.parseInt(String.valueOf(nameAndQuantity[i + 1])));
                        sizes.add(size);
                }
                return sizes;
        }

        // Helper method to create or retrieve parent categories
        private Category getOrCreateCategory(String name, int level, Category parent, CategoryRepo categoryRepo) {
                // First try to find existing category by name
                Category existing = categoryRepo.findByName(name);
                if (existing != null) {
                        logger.info("DATA INITIALIZER: Found existing category: " + name);
                        return existing;
                }
                // If not found, create new category
                Category category = new Category();
                category.setName(name);
                category.setLevel(level);
                if (parent != null) {
                        category.setParentCategory(parent);
                }
                Category saved = categoryRepo.save(category);
                logger.info("DATA INITIALIZER: Created new category: " + name);
                return saved;
        }

        // Helper method to create or retrieve sub-categories
        private Category createSubCategory(String id, String name, Category parent, CategoryRepo categoryRepo) {
                // First try to find existing category
                Category existing = categoryRepo.findByNameAndParent(id, parent.getName());
                if (existing != null) {
                        logger.info("DATA INITIALIZER: Found existing category: " + id);
                        return existing;
                }
                // If not found, create new category
                Category category = new Category();
                category.setName(id);
                category.setParentCategory(parent);
                // Set level based on parent's level + 1
                category.setLevel(parent.getLevel() + 1);
                Category saved = categoryRepo.save(category);
                logger.info("DATA INITIALIZER: Created new sub-category: " + id + " (Level " + category.getLevel()
                                + ")");
                return saved;
        }
}
