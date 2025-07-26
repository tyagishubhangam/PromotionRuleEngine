package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Represents the details of a promotion that can be returned to a player.
 * This is the payload shown when a rule matches.
 */
@Schema(description = "Represents the promotion details shown to the player")
@Data
public class PromotionPayload {

    /**
     * The user-facing title of the promotion.
     * Example: "Comeback Bonus", "Weekend Rush Promo"
     */
    @Schema(description = "Title of the promotion", example = "Comeback Bonus")
    private String title;

    /**
     * The actual reward offered in the promotion.
     * Can be coins, spins, chests, etc.
     * Example: "100 coins", "VIP Chest"
     */
    @Schema(description = "Reward offered in the promotion", example = "100 coins")
    private String reward;
}
