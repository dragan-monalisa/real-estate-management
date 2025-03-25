package com.realestate.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailBuilderTest {

    private final EmailBuilder emailBuilder = new EmailBuilder();

    @Test
    void confirmationEmailTest() {

        // when
        String result = emailBuilder.confirmationEmail("Alice Smith", "https://example.com/test");

        // then
        assertThat(result).contains("Alice Smith", "https://example.com/test", "Confirm your email");
    }

    @Test
    void forgotPasswordEmailTest() {

        // when
        String result = emailBuilder.forgotPasswordEmail("Alice Smith", "https://example.com/test");

        // then
        assertThat(result).contains("Alice Smith", "https://example.com/test", "Password reset");
    }

}
