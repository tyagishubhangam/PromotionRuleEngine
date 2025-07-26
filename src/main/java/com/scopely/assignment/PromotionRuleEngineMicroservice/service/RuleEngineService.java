package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;

/**
 * Interface for the core business logic of the promotion rule engine.
 * Handles evaluation of incoming player data against predefined promotion rules.
 */
public interface RuleEngineService {

    /**
     * Evaluates the given player attributes and returns a matching promotion if found.
     *
     * @param player The player request containing attributes like level, country, spend tier, etc.
     * @return A {@link PromotionPayload} object if a rule matches, or null if no match.
     */
    PromotionPayload evaluate(PlayerRequest player);

    /**
     * Provides a suggestion to the player if no promotion was matched.
     * For example: "Level up to 10+ to unlock Loyalty Reward".
     *
     * @param player The same player request used for evaluation.
     * @return A human-readable suggestion string to guide the player.
     */
    String suggestClosestRule(PlayerRequest player);
}
