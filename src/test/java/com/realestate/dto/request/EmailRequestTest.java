package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmailRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEmailTest() {
        // given
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void blankEmailTest() {
        // given
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("");

        // when
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    public void emailExceedsMaxLengthTest() {
        // given
        EmailRequest request = new EmailRequest();
        request.setEmail("t".repeat(65) + "@email.com");

        // when
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("size must be between 0 and 64");
    }

}
