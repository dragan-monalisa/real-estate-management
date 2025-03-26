package com.realestate.exception;

import com.realestate.dto.ErrorDto;
import com.realestate.dto.ValidationDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    private final RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    void handleEntityNotFoundException() {

        // given
        var exception = new EntityNotFoundException("error");

        // when
        ErrorDto result = restExceptionHandler.handle(exception);

        // then
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotNull();
    }

    @Test
    void handleResourceConflictException() {

        // given
        var exception = new ResourceConflictException("error");

        // when
        ErrorDto result = restExceptionHandler.handle(exception);

        // then
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotNull();
    }

    @Test
    void handleTechnicalException() {

        // given
        var exception = new TechnicalException("error");

        // when
        ErrorDto result = restExceptionHandler.handle(exception);

        // then
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotNull();
    }

    @Test
    void handleBusinessException() {

        // given
        var businessException = new BusinessException("error");

        // when
        ErrorDto result = restExceptionHandler.handle(businessException);

        // then
        assertThat(result.message()).isEqualTo("error");
        assertThat(result.timestamp()).isNotNull();
    }

    @Test
    void handleMethodArgumentNotValidExceptionTest() {

        // given
        var exception = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(BindingResult.class);
        var fieldError = new FieldError("name", "field", "error");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // when
        List<ValidationDto> result = restExceptionHandler.handle(exception);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().field()).isEqualTo("field");
        assertThat(result.getFirst().message()).isEqualTo("error");
        assertThat(result.getFirst().timestamp()).isNotBlank();
    }

}
