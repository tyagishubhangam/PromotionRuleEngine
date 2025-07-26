package com.scopely.assignment.PromotionRuleEngineMicroservice.exception;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.ExceptionResponse;
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

/**
 * Centralized exception handler for the entire application.
 * Handles validation errors and bad JSON requests gracefully.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for incoming requests with @Valid annotations.

     * Example: Missing or invalid fields in PlayerRequest.

     * @param ex MethodArgumentNotValidException thrown by validation framework
     * @return ResponseEntity with error and field-specific messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            // Avoid overwriting same field errors
            if (!errors.containsKey(error.getField())) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }

        ExceptionResponse response = ExceptionResponse.builder()
                .error("Validation Failed")
                .details(errors)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Handles malformed JSON or invalid enum values in request body.
     *
     * Example: Sending spendTier = "lowww" when only "LOW, MEDIUM, HIGH" are allowed.
     *
     * @param ex HttpMessageNotReadableException thrown by Jackson
     * @return ResponseEntity with error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ExceptionResponse response = ExceptionResponse.builder()
                .error("Malformed JSON Request")
                .details(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}
