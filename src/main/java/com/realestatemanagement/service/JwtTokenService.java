package com.realestatemanagement.service;

import com.realestatemanagement.entity.JwtToken;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.repository.JwtTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtTokenRepository jwtTokenRepository;

    public void saveJwtToken(User user, String jwtToken) {
        var token = JwtToken.builder()
                .user(user)
                .token(jwtToken)
                .build();

        jwtTokenRepository.save(token);
    }

    public void revokeAllUserJwtTokens(User user) {
        jwtTokenRepository.deleteAllByUser(user);
    }
}
