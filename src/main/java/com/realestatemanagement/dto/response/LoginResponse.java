package com.realestatemanagement.dto.response;

public record LoginResponse(String accessToken,
                            String refreshToken) {
}
