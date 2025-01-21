package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TechnicalExceptionTest {

    @Test
    void technicalExceptionMessageTest() {
        String message = "Technical exception";

        TechnicalException exception = new TechnicalException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void technicalExceptionIsThrownTest() {
        String message = "An error occurred";

        assertThatThrownBy(() -> {
                    throw new TechnicalException(message);
                }
        ).isInstanceOf(TechnicalException.class).hasMessage(message);
    }

}
