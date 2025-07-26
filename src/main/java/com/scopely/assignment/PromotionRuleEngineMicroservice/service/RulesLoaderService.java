package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;

import java.util.List;

/**
 * Service responsible for loading and providing access to promotion rules.
 * Rules are typically loaded from an in-memory YAML file and indexed for fast lookups.
 */
public interface RulesLoaderService {

    /**
     * Loads all promotion rules from the source (e.g., rules.yaml).
     * This can be invoked at application startup or via an API to reload at runtime.
     */
    void loadRules();

    /**
     * Returns the full list of loaded promotion rules.
     *
     * @return a List of all PromotionRule objects
     */
    List<PromotionRule> getRules();

    /**
     * Retrieves promotion rules filtered by both country and A/B testing bucket.
     * Returns a narrowed-down subset of rules for efficient evaluation.
     * If either input is null or not found, may fall back to returning all rules.
     *
     * @param country  the country code (e.g., "IN")
     * @param abBucket the A/B testing bucket (e.g., "A", "B", "C")
     * @return a filtered list of PromotionRule objects
     */
    List<PromotionRule> getRulesByCountryAndBucket(String country, String abBucket);
}
