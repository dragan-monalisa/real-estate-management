package com.realestate.constant;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TimestampPatternTest {

    private final LocalDateTime dateTime = LocalDateTime.of(2025, 1, 13, 15, 45, 25);
    private final String actual = "2025-01-13 15:45:25";

    @Test
    void dateTimeFormattingTest() {
        String formattedDate = dateTime.format(TimestampPattern.FORMATTER);

        assertThat(actual).isEqualTo(formattedDate);
    }

    @Test
    void dateTimeParsingTest() {
        LocalDateTime parsedDateTime = LocalDateTime.parse(actual, TimestampPattern.FORMATTER);

        assertThat(dateTime).isEqualTo(parsedDateTime);
    }

    @Test
    void invalidDateTimeParsingTest() {
        String invalidDateTime = "2024/09/03 14:30:45";

        assertThatThrownBy(() -> LocalDateTime.parse(invalidDateTime, TimestampPattern.FORMATTER))
                .isInstanceOf(DateTimeParseException.class);
    }

}
