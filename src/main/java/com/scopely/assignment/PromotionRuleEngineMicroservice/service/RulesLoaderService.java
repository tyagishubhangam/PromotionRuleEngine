package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;

import java.util.List;

public interface RulesLoaderService {
    void loadRules();
    List<PromotionRule> getRules();

    List<PromotionRule> getRulesByCountryAndBucket(String country, String abBucket);
}
