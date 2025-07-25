package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Schema(description = "Service evaluation metrics response")
@Getter
@Builder
@AllArgsConstructor
public class MetricsResponse {
    @Schema(description = "Total API calls")
    private final int totalEvaluations;

    @Schema(description = "Number of hits (rules matched)")
    private final int hits;

    @Schema(description = "Number of misses (no rules matched)")
    private final int misses;

    @Schema(description = "Average latency in ms")
    private final double averageLatencyMs;
}
