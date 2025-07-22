package com.strawhats.userregistrationloginsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("Error", "User with provided crdentials already exists!");
        response.put("Message", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> badCredentialsExceptionHandler(BadCredentialsException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("Error", "Invalid username or password");
        response.put("Message", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
