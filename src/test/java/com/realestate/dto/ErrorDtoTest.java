package com.realestate.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorDtoTest {

    @Test
    void errorDtoWithMessageTest() {

        // when
        var result = new ErrorDto("error");

        // then
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotBlank();
    }

}
