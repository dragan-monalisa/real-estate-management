package com.realestate.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorDtoTest {

    @Test
    void errorDtoWithMessageTest() {
        
        // when
        ErrorDto error = new ErrorDto("error");

        // then
        assertThat(error.message()).isEqualTo("error");
        assertThat(error.timestamp()).isNotNull();
    }

}
