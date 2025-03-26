package com.realestate.component;

import com.realestate.dto.ValidationDto;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstraintValidator {

    public static List<ValidationDto> buildErrors(Set<ConstraintViolation<?>> violations) {

        return violations.stream()
                .map(violation -> new ValidationDto(
                        Objects.requireNonNull(
                                StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                        .reduce((first, second) -> second)
                                        .orElse(null)
                        ).toString(),
                        violation.getMessage()
                )).collect(Collectors.toList());
    }

    public static List<ValidationDto> buildErrors(BindingResult bindingResult) {

        return bindingResult.getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ValidationDto(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                ).collect(Collectors.toList());
    }

}
