package com.realestate.constant;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class EmailRegexTest {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EmailRegex.EXPRESSION, Pattern.CASE_INSENSITIVE);

    @ParameterizedTest
    @ValueSource(strings = {
            "name@example.com",
            "firstname.lastname@example.com",
            "firstname-lastname@example.com",
            "firstname.last-name@example.com",
            "first-name-last-name@example.com",
            "user.name-user@example.com",
            "name@s.example",
            "admin@mailserver.com",
            "user-@example.org",
            "valid.email+alias@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.co.uk",
            "1234567890@example.com",
            "email@example.name",
            "email@example.museum",
            "email@example.travel"
    })
    void validEmailsTest_OK(String input) {
        assertThat(EMAIL_PATTERN.matcher(input).matches()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "user-@example-org",
            "username",
            "@missingusername.com",
            "username@.com",
            "username@yahoo.com.",
            "username@yahoo..com",
            ".username@yahoo.com",
            "username@yahoo1corporate",
            "username@.domain.com",
            "username@domain..com"
    })
    void invalidEmailsTest_InvalidEmails(String input) {
        assertThat(EMAIL_PATTERN.matcher(input).matches()).isFalse();
    }

}
