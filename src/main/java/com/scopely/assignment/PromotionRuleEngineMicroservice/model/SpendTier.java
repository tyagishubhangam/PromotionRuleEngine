package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

/**
 * Enum representing the player's spending tier.

 * Used in rule conditions to match players based on their in-game spending behavior.
 * Input is case-insensitive due to configuration in ObjectMapper.
 */
public enum SpendTier {
    /**
     * Low-spending player (e.g., infrequent or small purchases).
     */
    LOW,

    /**
     * Medium-spending player (e.g., moderate, regular purchases).
     */
    MEDIUM,

    /**
     * High-spending player (e.g., premium or frequent purchases).
     */
    HIGH
}
