package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ResetPasswordRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validPasswordTest() {

        // given
        var request = new ResetPasswordRequest();
        request.setPassword("password123456");

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void blankPasswordTest() {

        // given
        var request = new ResetPasswordRequest();
        request.setPassword("");

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    void passwordExceedsMaxLengthTest() {

        // given
        var request = new ResetPasswordRequest();
        request.setPassword("a".repeat(65));

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("size must be between 0 and 64");
    }

}
