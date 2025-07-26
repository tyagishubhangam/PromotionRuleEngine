package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Represents a promotion rule with matching conditions, promotion details,
 * optional time constraints, and a priority for conflict resolution.
 */
@Data
public class PromotionRule {

    /**
     * Unique identifier for the rule.
     * Helps with debugging, tracking, and admin operations.
     */
    private String id;

    /**
     * Conditions that must be satisfied for the rule to apply.
     * Includes filters like minLevel, country, spendTier, etc.
     */
    private Condition conditions;

    /**
     * The promotion payload that will be returned to the player
     * if the conditions are satisfied.
     */
    private PromotionPayload promotion;

    /**
     * Optional: The date-time from which this promotion rule becomes active.
     * Format is ISO 8601: yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * Optional: The date-time after which this promotion rule expires.
     * If null, the rule is considered valid indefinitely after startTime.
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * Priority for resolving conflicts when multiple rules match.
     * Higher number = higher priority.
     * Defaults to 0 if not specified.
     */
    @JsonProperty(defaultValue = "0")
    private Integer priority = 0;
}
