package com.realestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dto.request.EmailRequest;
import com.realestate.dto.request.LoginRequest;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.dto.response.LoginResponse;
import com.realestate.repository.UserRepository;
import com.realestate.service.AuthService;
import com.realestate.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void registerTest() throws Exception {

        // given
        var request = new RegisterRequest();
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setEmail("test@example.com");
        request.setPassword("test");

        doNothing().when(authService).register(request);

        // then
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void loginTest() throws Exception {

        // given
        var request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        var expectedResponse = new LoginResponse("access-token", "refresh-token");

        when(authService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void confirmAccountTest() throws Exception {

        // given
        var token = UUID.randomUUID().toString();

        doNothing().when(authService).confirmAccount(token);

        // then
        mockMvc.perform(get("/api/v1/auth/confirm-account")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void resendConfirmationEmailTest() throws Exception {

        // given
        var request = new EmailRequest();
        request.setEmail("test@example.com");

        doNothing().when(authService).resendConfirmationEmail(request);

        // then
        mockMvc.perform(post("/api/v1/auth/resend-confirmation-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    void refreshTokenTest() throws Exception {

        // given
        var expectedResponse = new LoginResponse("access-token", "refresh-token");

        when(authService.refreshToken(any(HttpServletRequest.class))).thenReturn(expectedResponse);

        // then
        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void forgotPasswordTest() throws Exception {

        // given
        var request = new EmailRequest();
        request.setEmail("test@example.com");

        doNothing().when(authService).forgotPassword(request);

        // then
        mockMvc.perform(post("/api/v1/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

}
