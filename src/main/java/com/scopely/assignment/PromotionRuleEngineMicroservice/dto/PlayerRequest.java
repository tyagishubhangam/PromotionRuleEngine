package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.SpendTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO representing the incoming request from the client/player.
 *
 * This request is evaluated against the promotion rules defined in rules.yaml.
 * It captures the player's current attributes like level, country, spending behavior, etc.
 */
@Schema(description = "Player request object containing player attributes")
@Data
public class PlayerRequest {

    /**
     * The level of the player.
     * Must be at least 1.
     */
    @Schema(description = "Player level", example = "6", minimum = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level must be at least 1")
    private int level;

    /**
     * The country code of the player (e.g., IN, US).
     */
    @Schema(description = "Player country code", example = "IN", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Country must not be null")
    private String country;

    /**
     * The spending tier of the player (e.g., LOW, MEDIUM, HIGH).
     * Enum is case-insensitive thanks to config.
     */
    @Schema(
            description = "Player spend tier (allowed: LOW, MEDIUM, HIGH). Case-insensitive.",
            example = "low"
    )
    private SpendTier spendTier;

    /**
     * Days since the player's last purchase.
     * Optional field.
     */
    @Schema(description = "Days since player's last purchase", example = "3")
    private int daysSinceLastPurchase;

    /**
     * A/B testing bucket (e.g., A, B, C).
     * Optional field used for experimentation.
     */
    @Schema(description = "A/B testing bucket assigned to the player", example = "A")
    private String abBucket;
}
