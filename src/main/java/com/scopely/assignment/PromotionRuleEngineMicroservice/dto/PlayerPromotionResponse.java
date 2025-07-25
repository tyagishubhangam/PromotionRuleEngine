package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayerPromotionResponse {
    private PromotionPayload promotion;
    private String suggestion;
}
