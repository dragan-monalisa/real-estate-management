package com.realestate.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailBuilderTest {

    private EmailBuilder emailBuilder;

    private final String name = "Alice Smith";
    private final String link = "https://example.com/test";

    @BeforeEach
    public void setUp() {
        emailBuilder = new EmailBuilder();
    }

    @Test
    void confirmationEmailTest() {
        String email = emailBuilder.confirmationEmail(name, link);

        assertThat(email).contains(name);
        assertThat(email).contains(link);
        assertThat(email).contains("Confirm your email");

    }

    @Test
    void forgotPasswordEmailTest() {
        String email = emailBuilder.forgotPasswordEmail(name, link);

        assertThat(email).contains(name);
        assertThat(email).contains(link);
        assertThat(email).contains("Password reset");
    }

}
