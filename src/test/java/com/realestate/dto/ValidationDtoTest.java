package com.realestate.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationDtoTest {

    @Test
    void validationDtoTest() {

        // when
        var result = new ValidationDto("field", "error");

        // then
        assertThat(result.field()).isEqualTo("field");
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotBlank();
    }

}
