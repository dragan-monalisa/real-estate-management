package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BusinessExceptionTest {

    @Test
    void businessExceptionIsThrownTest() {

        assertThatThrownBy(() -> {
            throw new BusinessException("message");
        })
                .isInstanceOf(BusinessException.class)
                .hasMessage("message");
    }

}
