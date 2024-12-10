package com.realestatemanagement.dto.response;

import com.realestatemanagement.constant.TimestampPattern;

import java.time.LocalDateTime;

public record ErrorDto(String message, String timestamp) {
    
    public ErrorDto(String message) {
        this(message, LocalDateTime.now().format(TimestampPattern.FORMATTER));
    }
}
