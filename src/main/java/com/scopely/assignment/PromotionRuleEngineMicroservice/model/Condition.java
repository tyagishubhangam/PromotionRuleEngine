package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import lombok.Data;

/**
 * Represents the set of conditions that must be met
 * by a player in order to qualify for a specific promotion.
 */
@Data
public class Condition {

    /**
     * Minimum player level required to qualify for this promotion.
     * If null, no minimum level restriction is applied.
     */
    private Integer minLevel;

    /**
     * Maximum player level allowed for this promotion.
     * If null, no maximum level restriction is applied.
     */
    private Integer maxLevel;

    /**
     * Player's spend tier (LOW, MEDIUM, HIGH).
     * If specified, the player's spend tier must match.
     */
    private SpendTier spendTier;

    /**
     * Country code (e.g., "IN", "US") that this promotion is valid for.
     * If specified, the player's country must match this value.
     */
    private String country;

    /**
     * Number of days since the player last made a purchase.
     * If specified, the player's value must match exactly.
     */
    private Integer daysSinceLastPurchase;

    /**
     * A/B testing bucket value (e.g., "A", "B", "C").
     * Used to segment users into test groups for experiments.
     */
    private String abBucket;
}
