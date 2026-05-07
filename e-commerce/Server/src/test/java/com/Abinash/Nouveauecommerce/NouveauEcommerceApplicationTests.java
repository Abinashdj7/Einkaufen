package com.Abinash.Nouveauecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.Abinash.Nouveauecommerce.config.TestConfig;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class NouveauEcommerceApplicationTests {

	@Test
	void contextLoads() {
	}

}
