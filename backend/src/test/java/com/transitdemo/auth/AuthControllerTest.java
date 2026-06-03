package com.transitdemo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transitdemo.users.User;
import com.transitdemo.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test — uses Flapdoodle embedded MongoDB.
 * Verifies auth endpoint behavior end-to-end with real Spring Security.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired ObjectMapper objectMapper;

    @Test
    void login_returnsToken_withValidCredentials() throws Exception {
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .email("test@transit.demo")
                .password(passwordEncoder.encode("testpass"))
                .fullName("Test User")
                .role(User.Role.OPERATOR)
                .createdAt(Instant.now())
                .build());

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("email", "test@transit.demo", "password", "testpass"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andExpect(jsonPath("$.fullName").value("Test User"))
                .andExpect(jsonPath("$.role").value("OPERATOR"));
    }

    @Test
    void login_returns401_withBadCredentials() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                Map.of("email", "nobody@transit.demo", "password", "wrongpass"))))
                .andExpect(status().is(401));
    }

    @Test
    void login_returns400_withMissingFields() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
