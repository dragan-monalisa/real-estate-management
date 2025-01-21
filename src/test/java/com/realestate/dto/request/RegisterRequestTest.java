package com.realestate.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RegisterRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validRegisterRequestTest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Carolina");
        request.setLastName("Smith");
        request.setEmail("test@test.com");
        request.setPassword("password123456");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(0);
    }

    @Test
    public void blankFieldsTest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("");
        request.setLastName("");
        request.setEmail("");
        request.setPassword("");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(5);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("must not be blank");
    }

    @Test
    public void fieldsExceedsMaxLengthTest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("a".repeat(33));
        request.setLastName("a".repeat(33));
        request.setEmail("a".repeat(65));
        request.setPassword("a".repeat(65));

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(5);
        assertThat(violations).extracting(ConstraintViolation::getMessage)
                .contains("size must be between 0 and 32", "size must be between 0 and 64", "email format is not valid");
    }

    @Test
    public void invalidEmailFormatTest() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("Jessyca");
        request.setLastName("Doe");
        request.setEmail("invalid-email-format");
        request.setPassword("password123456");

        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(request);
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting(ConstraintViolation::getMessage).contains("email format is not valid");
        assertThat(violations).extracting(violation -> violation.getPropertyPath().toString()).contains("email");
    }

}
