package com.realestate.dto;

import com.realestate.constant.TimestampPattern;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationDtoTest {

    @Test
    public void validationDtoWithFieldAndErrorTest() {
        var validationDto = new ValidationDto("field", "error");

        assertThat("field").isEqualTo(validationDto.field());
        assertThat("error").isEqualTo(validationDto.message());
        assertThat(validationDto.timestamp()).isNotNull();

        var parsedTimestamp = LocalDateTime.parse(validationDto.timestamp(), TimestampPattern.FORMATTER);

        assertThat(parsedTimestamp).isNotNull();
    }

    @Test
    public void validationDtoWithFieldErrorAndTimestampTest() {
        var timestamp = "2025-01-013T10:15:30";

        var validationDto = new ValidationDto("field", "error", timestamp);

        assertThat("field").isEqualTo(validationDto.field());
        assertThat("error").isEqualTo(validationDto.message());
        assertThat(timestamp).isEqualTo(validationDto.timestamp());
    }

}
