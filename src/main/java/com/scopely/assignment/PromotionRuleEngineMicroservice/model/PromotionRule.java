package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import lombok.Data;

@Data
public class PromotionRule {
    private String id;
    private Condition conditions;
    private PromotionPayload promotion;
}
