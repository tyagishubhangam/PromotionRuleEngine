package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private String error;
    private Map<String, String> details;
}
