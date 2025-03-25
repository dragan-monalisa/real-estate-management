package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ResetPasswordRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validPasswordTest() {
        // given
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("password123456");

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    public void blankPasswordTest() {
        // given
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("");

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    public void passwordExceedsMaxLengthTest() {
        // given
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("a".repeat(65));

        // when
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("size must be between 0 and 64");
    }

}
