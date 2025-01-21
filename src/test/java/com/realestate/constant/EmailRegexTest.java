package com.realestate.constant;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailRegexTest {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EmailRegex.EXPRESSION, Pattern.CASE_INSENSITIVE);

    @Test
    void validEmailsTest() {
        assertThat(EMAIL_PATTERN.matcher("name@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("firstname.lastname@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("firstname-lastname@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("firstname.last-name@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("first-name-last-name@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("user.name-user@example.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("name@s.example").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("admin@mailserver.com").matches()).isTrue();
        assertThat(EMAIL_PATTERN.matcher("user-@example.org").matches()).isTrue();
    }

    @Test
    void invalidEmailsTest() {
        assertThat(EMAIL_PATTERN.matcher("user-@example-org").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("@missingusername.com").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@.com").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@yahoo.com.").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@yahoo..com").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher(".username@yahoo.com").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@yahoo1corporate").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@.domain.com").matches()).isFalse();
        assertThat(EMAIL_PATTERN.matcher("username@domain..com").matches()).isFalse();
    }

}
