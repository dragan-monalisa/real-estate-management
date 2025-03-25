package com.realestate.component;

import com.realestate.dto.ValidationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConstraintValidatorTest {

    @Mock
    private BindingResult bindingResult;

    @Test
    void buildErrorsBindingResultTest() {
        // given
        var fieldError = new FieldError("name", "field", "error");

        // when
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        List<ValidationDto> result = ConstraintValidator.buildErrors(bindingResult);

        // then
        assertThat(result).hasSize(1);

        assertThat(result.getFirst()).satisfies(validationDto -> {
            assertThat(validationDto.field()).isEqualTo("field");
            assertThat(validationDto.message()).isEqualTo("error");
            assertThat(validationDto.timestamp()).isNotNull();
        });
    }

}