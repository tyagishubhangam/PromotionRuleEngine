package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import lombok.Data;

@Data
public class Condition {
    private Integer minLevel;
    private Integer maxLevel;
    private String spendTier;
    private String country;
    private Integer daysSinceLastPurchase;
    private String abBucket;

}
