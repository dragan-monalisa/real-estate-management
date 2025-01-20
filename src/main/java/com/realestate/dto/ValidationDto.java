package com.realestate.dto;

import com.realestate.constant.TimestampPattern;

import java.time.LocalDateTime;

public record ValidationDto(String field,
                            String message,
                            String timestamp) {

    public ValidationDto(String field, String error) {
        this(field, error, LocalDateTime.now().format(TimestampPattern.FORMATTER));
    }

}
