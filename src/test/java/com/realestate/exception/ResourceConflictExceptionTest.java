package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResourceConflictExceptionTest {

    @Test
    void resourceConflictExceptionIsThrownTest() {

        assertThatThrownBy(() -> {
            throw new ResourceConflictException("message");
        })
                .isInstanceOf(ResourceConflictException.class)
                .hasMessage("message");
    }

}
