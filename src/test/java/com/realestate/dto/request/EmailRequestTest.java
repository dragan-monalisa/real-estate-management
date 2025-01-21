package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validEmailTest() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("test@test.com");

        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    void blankEmailTest() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setEmail("");

        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    public void emailExceedsMaxLengthTest() {
        EmailRequest request = new EmailRequest();
        request.setEmail("t".repeat(65) + "@email.com");

        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("size must be between 0 and 64");
    }

}
