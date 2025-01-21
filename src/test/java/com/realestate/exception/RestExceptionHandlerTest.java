package com.realestate.exception;

import com.realestate.component.ConstraintValidator;
import com.realestate.dto.ErrorDto;
import com.realestate.dto.ValidationDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
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

    private RestExceptionHandler restExceptionHandler;

    @BeforeEach
    void setup() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void handleEntityNotFoundException() {
        String message = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(message);

        ErrorDto response = restExceptionHandler.handle(exception);

        assertEquals(message, response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleResourceConflictException() {
        String message = "Resource conflict";
        ResourceConflictException exception = new ResourceConflictException(message);

        ErrorDto response = restExceptionHandler.handle(exception);

        assertEquals(message, response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleTechnicalAndBusinessExceptions() {
        String message = "Technical error";
        TechnicalException exception = new TechnicalException(message);

        ErrorDto response = restExceptionHandler.handle(exception);

        assertEquals(message, response.message());
        assertNotNull(response.timestamp());

        message = "Business error";
        BusinessException businessException = new BusinessException(message);

        response = restExceptionHandler.handle(businessException);

        assertEquals(message, response.message());
        assertNotNull(response.timestamp());
    }

    @Test
    void handleMethodArgumentNotValidException() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);

        ValidationDto validationDto = new ValidationDto("field", "error message");
        List<ValidationDto> validationErrors = List.of(validationDto);

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
        ConstraintViolationException exception = mock(ConstraintViolationException.class);
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);

        when(exception.getConstraintViolations()).thenReturn(Set.of(violation));

        ValidationDto validationDto = new ValidationDto("field", "error message");
        List<ValidationDto> validationErrors = List.of(validationDto);

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
