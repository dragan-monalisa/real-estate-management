package com.realestate.exception;

import com.realestate.component.ConstraintValidator;
import com.realestate.dto.ErrorDto;
import com.realestate.dto.ValidationDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestExceptionHandlerTest {

    private final RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    void handleEntityNotFoundException() {
        // given
        EntityNotFoundException exception = new EntityNotFoundException("error");

        // when
        ErrorDto response = restExceptionHandler.handle(exception);

        // then
        assertEquals("error", response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleResourceConflictException() {
        // given
        ResourceConflictException exception = new ResourceConflictException("error");

        // when
        ErrorDto response = restExceptionHandler.handle(exception);

        // then
        assertEquals("error", response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleTechnicalAndBusinessExceptions() {
        // given
        TechnicalException exception = new TechnicalException("Technical error");

        // when
        ErrorDto response = restExceptionHandler.handle(exception);

        // then
        assertEquals("Technical error", response.message());
        assertNotNull(response.timestamp());

        // given
        BusinessException businessException = new BusinessException("Business error");

        // when
        response = restExceptionHandler.handle(businessException);

        // then
        assertEquals("Business error", response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        // given
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // when
        when(exception.getBindingResult()).thenReturn(bindingResult);

        ValidationDto validationDto = new ValidationDto("field", "error message");
        List<ValidationDto> validationErrors = List.of(validationDto);

        // then
        try (MockedStatic<ConstraintValidator> mockedStatic = mockStatic(ConstraintValidator.class)) {
            mockedStatic.when(() -> ConstraintValidator.buildErrors(bindingResult))
                    .thenReturn(validationErrors);

            List<ValidationDto> response = restExceptionHandler.handle(exception);

            assertEquals(1, response.size());
            assertEquals("field", response.getFirst().field());
            assertEquals("error message", response.getFirst().message());
            assertNotNull(response.getFirst().timestamp());
        }
    }

    @Test
    void handleConstraintViolationException() {
        // given
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        // when
        when(exception.getConstraintViolations()).thenReturn(Set.of(violation));

        ValidationDto validationDto = new ValidationDto("field", "error message");
        List<ValidationDto> validationErrors = List.of(validationDto);

        // then
        try (MockedStatic<ConstraintValidator> mockedStatic = mockStatic(ConstraintValidator.class)) {
            mockedStatic.when(() -> ConstraintValidator.buildErrors(exception.getConstraintViolations()))
                    .thenReturn(validationErrors);

            List<ValidationDto> response = restExceptionHandler.handle(exception);

            assertEquals(1, response.size());
            assertEquals("field", response.getFirst().field());
            assertEquals("error message", response.getFirst().message());
            assertNotNull(response.getFirst().timestamp());
        }
    }

}
