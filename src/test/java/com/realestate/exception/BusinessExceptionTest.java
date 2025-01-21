package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BusinessExceptionTest {

    @Test
    void businessExceptionMessageTest() {
        String message = "Business exception";

        BusinessException businessException = new BusinessException(message);

        assertThat(businessException.getMessage()).isEqualTo(message);
    }

    @Test
    void businessExceptionIsThrownTest() {
        String message = "An error occurred";

        assertThatThrownBy(() -> {
                    throw new BusinessException(message);
                }
        ).isInstanceOf(BusinessException.class).hasMessage(message);
    }

}
