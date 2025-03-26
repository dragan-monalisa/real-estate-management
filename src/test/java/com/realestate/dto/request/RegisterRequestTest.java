package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void validFieldsTest() {

        // given
        var request = new RegisterRequest();
        request.setFirstName("Carolina");
        request.setLastName("Smith");
        request.setEmail("test@test.com");
        request.setPassword("password123456");

        // when
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
    }

    @Test
    void blankFieldsTest() {

        // given
        var request = new RegisterRequest();
        request.setFirstName("");
        request.setLastName("");
        request.setEmail("");
        request.setPassword("");

        // when
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(5);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank", "email format is not valid");
    }

    @Test
    void fieldsExceedsMaxLengthTest() {

        // given
        var request = new RegisterRequest();
        request.setFirstName("a".repeat(33));
        request.setLastName("a".repeat(33));
        request.setEmail("a".repeat(65));
        request.setPassword("a".repeat(65));

        // when
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);

        // then
        assertThat(violations).hasSize(5);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("size must be between 0 and 32", "size must be between 0 and 64", "email format is not valid");
    }

    @Test
    void invalidEmailFormatTest() {

        // given
        var request = new RegisterRequest();
        request.setFirstName("Jessyca");
        request.setLastName("Doe");
        request.setEmail("invalid-email-format");
        request.setPassword("password123456");

        // when
        Set<ConstraintViolation<RegisterRequest>> result = validator.validate(request);

        // then
        assertThat(result).hasSize(1);
        assertThat(result).extracting(ConstraintViolation::getMessage).contains("email format is not valid");
    }

}
