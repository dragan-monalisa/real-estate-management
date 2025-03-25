package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TechnicalExceptionTest {

    @Test
    void technicalExceptionIsThrownTest() {

        assertThatThrownBy(() -> {
            throw new TechnicalException("message");
        })
                .isInstanceOf(TechnicalException.class)
                .hasMessage("message");
    }

}
