package com.realestate.exception;


import com.realestate.component.ConstraintValidator;
import com.realestate.dto.ErrorDto;
import com.realestate.dto.ValidationDto;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@Hidden
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handle(EntityNotFoundException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handle(ResourceConflictException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler({
            TechnicalException.class,
            BusinessException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handle(RuntimeException e) {
        log.error("bad request: {}", e.getMessage());

        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ValidationDto> handle(MethodArgumentNotValidException e) {
        return ConstraintValidator.buildErrors(e.getBindingResult());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public List<ValidationDto> handle(ConstraintViolationException e) {
        return ConstraintValidator.buildErrors(e.getConstraintViolations());
    }

}
