package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO for sending structured error responses from the API.

 * This is used by the global exception handler to return clear
 * and consistent error messages to the client.

 * Fields:
 * - error: A short message describing the type of error (e.g. "Validation failed")
 * - details: Detailed information about the error (can be a String or a Map of field-specific errors)
 */
@Builder
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private String error;
    private Object details;
}
