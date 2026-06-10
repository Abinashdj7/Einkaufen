package com.Abinash.testsupport;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import com.Abinash.Nouveauecommerce.Repo.CartRepo;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public CartRepo cartRepo() {
        return Mockito.mock(CartRepo.class);
    }
}
