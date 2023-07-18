package org.javakid.Finder.controllers.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        Map<String, List<String>> fieldsMap = e.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return new ResponseEntity<>(fieldsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<Path, List<String>>> handle(ConstraintViolationException e) {
        log.info(e.getMessage());
        Map<Path, List<String>> pathsMap = e.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(ConstraintViolation::getPropertyPath,
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));
        return new ResponseEntity<>(pathsMap, HttpStatus.BAD_REQUEST);
    }
}
