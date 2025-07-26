package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.SpendTier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Schema(description = "Player request object containing player attributes")
@Data
public class PlayerRequest {
    @Schema(description = "Player level", example = "6")
    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level must be at least 1")
    private int level;

    @Schema(description = "Player country code", example = "IN")
    @NotNull(message = "Country must not be null")
    private String country;


    @Schema(
            description = "Player spend tier (allowed: LOW, MEDIUM, HIGH). Case-insensitive.",
            example = "low"
    )
    private SpendTier spendTier;

    @Schema(description = "Days since player's last purchase", example = "3")
    private int daysSinceLastPurchase;

    @Schema(description = "A/B testing bucket assigned to the player", example = "A")
    private String abBucket;

}
