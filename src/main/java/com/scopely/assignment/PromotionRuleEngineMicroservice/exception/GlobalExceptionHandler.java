package com.scopely.assignment.PromotionRuleEngineMicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();

            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                // Add only if not already present (avoid duplicate keys)
                if (!errors.containsKey(error.getField())) {
                    errors.put(error.getField(), error.getDefaultMessage());
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Validation failed");
            response.put("details", errors);

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Malformed request body");
        error.put("details", ex.getMessage()); // more readable message

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    }

