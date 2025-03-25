package com.realestate.constant;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TimestampPatternTest {

    private static final LocalDateTime dateTime = LocalDateTime.of(2025, 1, 13, 15, 45, 25);
    private static final String expected = "2025-01-13 15:45:25";

    @Test
    void dateTimeFormattingTest() {

        // given
        String result = dateTime.format(TimestampPattern.FORMATTER);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void dateTimeParsingTest() {

        // given
        LocalDateTime result = LocalDateTime.parse(expected, TimestampPattern.FORMATTER);

        // then
        assertThat(result).isEqualTo(dateTime);
    }

    @Test
    void invalidDateTimeParsingTest() {

        // given
        String invalidDateTime = "2024/09/03 14:30:45";

        // then
        assertThatThrownBy(() -> LocalDateTime.parse(invalidDateTime, TimestampPattern.FORMATTER))
                .isInstanceOf(DateTimeParseException.class);
    }

}
