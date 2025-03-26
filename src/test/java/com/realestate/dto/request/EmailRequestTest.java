package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmailRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private final EmailRequest emailRequest = new EmailRequest();

    @Test
    void validEmailTest() {

        // given
        emailRequest.setEmail("test@test.com");

        // when
        Set<ConstraintViolation<EmailRequest>> result = validator.validate(emailRequest);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void blankEmailTest() {

        // given
        emailRequest.setEmail("");

        // when
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    void emailExceedsMaxLengthTest() {

        // given
        emailRequest.setEmail("t".repeat(65) + "@email.com");

        // when
        Set<ConstraintViolation<EmailRequest>> violations = validator.validate(emailRequest);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("size must be between 0 and 64");
    }

}
