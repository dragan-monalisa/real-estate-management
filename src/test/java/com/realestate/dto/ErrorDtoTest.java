package com.realestate.dto;

import com.realestate.constant.TimestampPattern;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorDtoTest {

    @Test
    public void errorDtoWithMessageTest() {
        String message = "An error occurred";
        ErrorDto errorDto = new ErrorDto(message);

        assertThat(message).isEqualTo(errorDto.message());
        assertThat(errorDto.timestamp()).isNotNull();

        LocalDateTime parsedTimestamp = LocalDateTime.parse(errorDto.timestamp(), TimestampPattern.FORMATTER);
        assertThat(parsedTimestamp).isNotNull();
    }

    @Test
    public void errorDtoWithMessageAndTimestampTest() {
        String message = "Another error";
        String timestamp = "2025-01-013T10:15:30";

        ErrorDto errorDto = new ErrorDto(message, timestamp);

        assertThat(message).isEqualTo(errorDto.message());
        assertThat(timestamp).isEqualTo(errorDto.timestamp());
    }

}
