package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PromotionRule {
    private String id;
    private Condition conditions;
    private PromotionPayload promotion;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    @JsonProperty(defaultValue = "0")
    private Integer priority = 0;

}
