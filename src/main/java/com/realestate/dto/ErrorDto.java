package com.realestate.dto;

import com.realestate.constant.TimestampPattern;

import java.time.LocalDateTime;

public record ErrorDto(String message, String timestamp) {

    public ErrorDto(String message) {
        this(message, LocalDateTime.now().format(TimestampPattern.FORMATTER));
    }

}
