package com.realestatemanagement.exception;

import com.realestatemanagement.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handle(EntityNotFoundException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto handle(ResourceConflictException e) {
        log.error("resource conflict: {}", e.getMessage());

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
}
