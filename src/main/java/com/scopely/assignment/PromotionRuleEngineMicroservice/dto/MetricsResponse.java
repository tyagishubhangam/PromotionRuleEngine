package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing service metrics for the Promotion Rule Engine.
 *
 * This class is returned by the `/metrics` endpoint and helps monitor
 * API performance, accuracy, and load.
 */
@Schema(description = "Service evaluation metrics response")
@Getter
@Builder
@AllArgsConstructor
public class MetricsResponse {

    /**
     * Total number of times the promotion evaluation API was called.
     */
    @Schema(description = "Total API calls")
    private final int totalEvaluations;

    /**
     * Number of times a promotion rule successfully matched a player.
     */
    @Schema(description = "Number of hits (rules matched)")
    private final int hits;

    /**
     * Number of times no promotion rule matched a player's request.
     */
    @Schema(description = "Number of misses (no rules matched)")
    private final int misses;

    /**
     * Average response time (in milliseconds) taken by the evaluation process.
     */
    @Schema(description = "Average latency in ms")
    private final double averageLatencyMs;
}
