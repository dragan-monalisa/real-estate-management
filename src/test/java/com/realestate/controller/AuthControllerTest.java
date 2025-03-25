package com.realestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dto.request.EmailRequest;
import com.realestate.dto.request.LoginRequest;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.dto.response.LoginResponse;
import com.realestate.repository.UserRepository;
import com.realestate.service.AuthService;
import com.realestate.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

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

    private static final String PATH = "/api/v1/auth";

    @Test
    void registerTest() throws Exception {
        // given
        var request = new RegisterRequest();

        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setEmail("test@example.com");
        request.setPassword("test");

        // when
        doNothing().when(authService).register(any(RegisterRequest.class));

        // then
        mockMvc.perform(post(PATH + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isCreated());
    }

    @Test
    void loginTest() throws Exception {
        // given
        LoginRequest request = new LoginRequest();

        request.setEmail("test@example.com");
        request.setPassword("password");

        LoginResponse expectedResponse = new LoginResponse("access-token", "refresh-token");

        // when
        when(authService.login(any(LoginRequest.class))).thenReturn(expectedResponse);

        // then
        mockMvc.perform(post(PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.refreshToken").value("refresh-token"));
    }

    @Test
    void confirmAccountTest() throws Exception {
        // given
        String token = UUID.randomUUID().toString();

        // when
        doNothing().when(authService).confirmAccount(anyString());

        // then
        mockMvc.perform(get(PATH + "/confirm-account")
                .param("token", token)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void resendConfirmationEmailTest() throws Exception {
        // given
        EmailRequest request = new EmailRequest();
        request.setEmail("test@example.com");

        // when
        doNothing().when(authService).resendConfirmationEmail(request);

        // then
        mockMvc.perform(post(PATH + "/resend-confirmation-email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

}
