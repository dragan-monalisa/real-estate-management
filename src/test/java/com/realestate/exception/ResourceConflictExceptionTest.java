package com.realestate.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceConflictExceptionTest {

    @Test
    void resourceConflictExceptionMessageTest() {
        String message = "Resource conflict exception";

        ResourceConflictException exception = new ResourceConflictException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void resourceConflictExceptionIsThrownTest() {
        String message = "An error occurred";

        assertThatThrownBy(() -> {
                    throw new ResourceConflictException(message);
                }
        ).isInstanceOf(ResourceConflictException.class).hasMessage(message);
    }

}
