package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;

public interface RuleEngineService {
    PromotionPayload evaluate(PlayerRequest player);
    String suggestClosestRule(PlayerRequest player);
}
