package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "Player request object containing player attributes")
@Data
public class PlayerRequest {
    @Schema(description = "Player level", example = "6")
    private int level;

    @Schema(description = "Player country code", example = "IN")
    private String country;

    @Schema(description = "Player spend tier", example = "low")
    private String spendTier;

    @Schema(description = "Days since player's last purchase", example = "3")
    private int daysSinceLastPurchase;
}
