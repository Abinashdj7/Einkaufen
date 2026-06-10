package com.Abinash.Nouveauecommerce.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.Abinash.Nouveauecommerce.Model.Cart;
import com.Abinash.Nouveauecommerce.Model.User;
import com.Abinash.Nouveauecommerce.Repo.CartRepo;
import com.Abinash.Nouveauecommerce.Repo.UserRepo;
import com.Abinash.Nouveauecommerce.Request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Exercises the signup/login flow end to end against a real (H2) database:
 * Controller -> Service -> Repository -> JPA -> JWT, with no mocked layers.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    private String signupPayload(String email, String password) throws Exception {
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail(email);
        user.setPassword(password);
        return objectMapper.writeValueAsString(user);
    }

    @Test
    @DisplayName("signup persists the user with a hashed password, creates a cart, and returns a JWT")
    void signup_persistsUserCreatesCartAndReturnsJwt() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupPayload("jane.doe@example.com", "Secret123")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Sign up successful"))
                .andExpect(jsonPath("$.jwt").isNotEmpty());

        User saved = userRepo.findByEmail("jane.doe@example.com");
        assertNotNull(saved, "user should be persisted in the database");
        assertNotEquals("Secret123", saved.getPassword(), "raw password must not be stored");

        Cart cart = cartRepo.findByUserId(saved.getId());
        assertNotNull(cart, "a cart should be created for the new user");
        assertEquals(saved.getId(), cart.getUser().getId());
    }

    @Test
    @DisplayName("signup rejects an email that is already registered")
    void signup_rejectsDuplicateEmail() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupPayload("duplicate@example.com", "Secret123")))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupPayload("duplicate@example.com", "AnotherPass1")))
                .andExpect(status().is4xxClientError());

        assertEquals(1, userRepo.findAll().stream()
                .filter(u -> "duplicate@example.com".equals(u.getEmail()))
                .count(), "duplicate signup must not create a second user record");
    }

    @Test
    @DisplayName("login with correct credentials returns a JWT for a previously registered user")
    void login_withCorrectCredentials_returnsJwt() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupPayload("login.user@example.com", "Secret123")))
                .andExpect(status().isCreated());

        String loginPayload = objectMapper.writeValueAsString(
                new LoginRequest("login.user@example.com", "Secret123"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Sign in successful"))
                .andExpect(jsonPath("$.jwt").isNotEmpty());
    }

    @Test
    @DisplayName("login with an incorrect password is rejected")
    void login_withWrongPassword_isRejected() throws Exception {
        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signupPayload("wrongpass.user@example.com", "Secret123")))
                .andExpect(status().isCreated());

        String loginPayload = objectMapper.writeValueAsString(
                new LoginRequest("wrongpass.user@example.com", "totally-wrong"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("login for an unknown email is rejected")
    void login_withUnknownEmail_isRejected() throws Exception {
        String loginPayload = objectMapper.writeValueAsString(
                new LoginRequest("nobody@example.com", "whatever1"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginPayload))
                .andExpect(status().is4xxClientError());
    }
}
