package com.realestatemanagement.controller;

import com.realestatemanagement.dto.request.EmailRequest;
import com.realestatemanagement.dto.request.LoginRequest;
import com.realestatemanagement.dto.request.RegisterRequest;
import com.realestatemanagement.dto.request.ResetPasswordRequest;
import com.realestatemanagement.dto.response.LoginResponse;
import com.realestatemanagement.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request) {
        service.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("confirm-account")
    public void confirmAccount(@RequestParam String token) {
        service.confirmAccount(token);
    }

    @PostMapping("/resend-confirmation-email")
    public void resendConfirmationEmail(@Valid @RequestBody EmailRequest request) {
        service.resendConfirmationEmail(request);
    }

    @PostMapping("/refresh-token")
    public LoginResponse refreshToken(HttpServletRequest request) {
        return service.refreshToken(request);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody EmailRequest request) {
        service.resendConfirmationEmail(request);
    }

    @PatchMapping("/reset-password")
    public void resetPassword(@RequestParam String token,
                              @Valid @RequestBody ResetPasswordRequest request) {
        service.resetPassword(token, request);
    }
}
