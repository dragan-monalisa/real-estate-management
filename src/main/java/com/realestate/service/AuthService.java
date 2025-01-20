package com.realestate.service;

import com.realestate.component.EmailBuilder;
import com.realestate.dto.request.EmailRequest;
import com.realestate.dto.request.LoginRequest;
import com.realestate.dto.request.RegisterRequest;
import com.realestate.dto.request.ResetPasswordRequest;
import com.realestate.dto.response.LoginResponse;
import com.realestate.entity.User;
import com.realestate.entity.UuidToken;
import com.realestate.exception.BusinessException;
import com.realestate.exception.ResourceConflictException;
import com.realestate.exception.TechnicalException;
import com.realestate.repository.UserRepository;
import com.realestate.repository.UuidTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtTokenService jwtTokenService;
    private final UuidTokenRepository uuidTokenRepository;
    private final EmailBuilder emailBuilder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    @Value("${host.port}")
    private String hostAndPort;

    @Value("${uuid.token.expiration}")
    private int expireTime;

    public void register(RegisterRequest request) {
        String email = request.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new ResourceConflictException("Email already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        jwtTokenService.saveJwtToken(user, jwtToken);
        sendConfirmationEmail(user);
    }

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        User user = userRepository.getOptionalByEmail(email).orElseThrow(
                () -> new BusinessException("Invalid email or password"));

        if (user.isEnabled()) {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                request.getPassword()
                        )
                );
            } catch (AuthenticationException e) {
                throw new BusinessException("Invalid email or password");
            }

            String accessToken = jwtService.generateToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            jwtTokenService.revokeAllUserJwtTokens(user);
            jwtTokenService.saveJwtToken(user, accessToken);

            return new LoginResponse(accessToken, refreshToken);
        }

        throw new BusinessException("Account activation is required before login");
    }

    @Transactional
    public void confirmAccount(String token) {
        UuidToken uuidToken = uuidTokenRepository.getByToken(token);
        uuidToken.setExpiresAt(LocalDateTime.now());

        userRepository.enableUser(uuidToken.getUser().getId());
    }

    public void resendConfirmationEmail(EmailRequest request) {
        Optional<User> user = userRepository.findUnconfirmedUser(request.getEmail());

        if (user.isEmpty()) {
            return;
        }

        List<UuidToken> tokens = uuidTokenRepository.findAllByUser(user.get());

        if (!tokens.isEmpty()) {
            throw new BusinessException("A confirmation email was already sent");
        }

        sendConfirmationEmail(user.get());
    }

    public LoginResponse refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TechnicalException("Invalid or missing authorization header");
        }

        String refreshToken = authHeader.substring(7);
        String email = jwtService.extractUsername(refreshToken);

        if (email != null) {
            User user = userRepository.getByEmail(email);

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                jwtTokenService.revokeAllUserJwtTokens(user);
                jwtTokenService.saveJwtToken(user, accessToken);

                return new LoginResponse(accessToken, refreshToken);
            }
        }

        throw new TechnicalException("Failed to extract user details from header");
    }

    public void forgotPassword(EmailRequest request) {
        Optional<User> user = userRepository.getValidOptionalByEmail(request.getEmail());

        if (user.isEmpty()) {
            return;
        }

        String token = UUID.randomUUID().toString();
        String link = hostAndPort + "/reset-password?resetToken=" + token;

        var resetToken = UuidToken.builder()
                .token(token)
                .expiresAt(LocalDateTime.now().plusMinutes(expireTime))
                .user(user.get())
                .build();

        uuidTokenRepository.save(resetToken);

        String name = user.get().getFirstName();
        String builtEmail = emailBuilder.forgotPasswordEmail(name, link);

        emailService.send(request.getEmail(), builtEmail);
    }

    public void resetPassword(String token, ResetPasswordRequest request) {
        UuidToken uuidToken = uuidTokenRepository.getByToken(token);

        uuidTokenRepository.delete(uuidToken);

        String email = uuidToken.getUser().getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        userRepository.changePassword(email, password);
    }

    private void sendConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        String link = hostAndPort + "api/v1/auth/confirm-account?token=" + token;

        var uuidToken = UuidToken.builder()
                .token(token)
                .expiresAt(LocalDateTime.now().plusMinutes(expireTime))
                .user(user)
                .build();

        uuidTokenRepository.save(uuidToken);

        String emailBody = emailBuilder.confirmationEmail(user.getFirstName(), link);
        emailService.send(user.getEmail(), emailBody);
    }

}
