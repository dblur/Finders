package org.javakid.Finder.controllers.advice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.javakid.Finder.exceptions.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private static final String MESSAGE_KEY = "message";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e) {
        log.info(e.getMessage());
        Map<String, List<String>> invalidFields = e.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        return new ResponseEntity<>(invalidFields, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<Path, List<String>>> handle(ConstraintViolationException e) {
        log.info(e.getMessage());
        Map<Path, List<String>> invalidFields = e.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(ConstraintViolation::getPropertyPath,
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())));
        return new ResponseEntity<>(invalidFields, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, String>> handle(AuthException e) {
        log.info(e.getMessage());
        Map<String, String> map = Map.of(MESSAGE_KEY, e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handle(UsernameNotFoundException e) {
        log.info(e.getMessage());
        Map<String, String> map = Map.of(MESSAGE_KEY, e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handle(AuthenticationException e) {
        log.info(e.getMessage());
        Map<String, String> map = Map.of(MESSAGE_KEY, e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handle(AccessDeniedException e) {
        log.info(e.getMessage());
        Map<String, String> map = Map.of(MESSAGE_KEY, e.getMessage());
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }
}
