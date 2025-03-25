package com.realestate.component;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailBuilderTest {

    private final EmailBuilder emailBuilder = new EmailBuilder();

    @Test
    void confirmationEmailTest() {
        // when
        String email = emailBuilder.confirmationEmail("Alice Smith", "https://example.com/test");

        // then
        assertThat(email).contains("Alice Smith");
        assertThat(email).contains("https://example.com/test");
        assertThat(email).contains("Confirm your email");
    }

    @Test
    void forgotPasswordEmailTest() {
        // when
        String email = emailBuilder.forgotPasswordEmail("Alice Smith", "https://example.com/test");

        // then
        assertThat(email).contains("Alice Smith");
        assertThat(email).contains("https://example.com/test");
        assertThat(email).contains("Password reset");
    }

}