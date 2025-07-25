package com.scopely.assignment.PromotionRuleEngineMicroservice.controller;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.MetricsResponse;
import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerPromotionResponse;
import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.metrics.MetricsService;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import com.scopely.assignment.PromotionRuleEngineMicroservice.service.RuleEngineService;
import com.scopely.assignment.PromotionRuleEngineMicroservice.service.RulesLoaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Promotion Rule Engine", description = "APIs for evaluating and managing promotion rules")

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PromotionController {
    private final RuleEngineService ruleEngineService;
    private final RulesLoaderService rulesLoaderService;
    private final MetricsService metricsService;


    @Operation(summary = "Get promotion for player", description = "Evaluates player attributes and returns matching promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promotion matched and returned",content = @Content(schema = @Schema(implementation = PlayerPromotionResponse.class))),
            @ApiResponse(responseCode = "204", description = "No matching promotion found", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content()),
    })
    @PostMapping("/promotion")
    public ResponseEntity<PlayerPromotionResponse> getPlayerPromotion(@RequestBody PlayerRequest playerRequest) {
        long start = System.currentTimeMillis();
        PromotionPayload result = ruleEngineService.evaluate(playerRequest);
        long latency = System.currentTimeMillis() - start;

        boolean hit = result != null;
        metricsService.recordEvaluation(hit, latency);
        if (result == null) {
            // Attempt to find a suggestion
            String suggestion = ruleEngineService.suggestClosestRule(playerRequest);
            return ResponseEntity.ok(
                    PlayerPromotionResponse.builder()
                            .promotion(null)
                            .suggestion(suggestion)
                            .build()
            );
        }
        return ResponseEntity.ok(
                PlayerPromotionResponse.builder()
                        .promotion(result)
                        .suggestion(null)
                        .build()
        );
    }

    @Operation(summary = "Get evaluation metrics", description = "Returns metrics like total evaluations, hits, misses, and average latency")
    @GetMapping("/metrics")
    public ResponseEntity<MetricsResponse> getPromotionMetrics() {
        return ResponseEntity.ok(MetricsResponse.builder()
                .totalEvaluations(metricsService.getTotalEvaluations())
                .hits(metricsService.getHits())
                .misses(metricsService.getMisses())
                .averageLatencyMs(metricsService.getAverageLatency())
                .build());
    }


    @Operation(summary = "Reload rules from YAML", description = "Reloads rules.yaml at runtime without restarting the app")
    @PostMapping("/reloadRules")
    public ResponseEntity<String> reloadRules() {
        rulesLoaderService.loadRules();
        return ResponseEntity.ok("Rules reloaded successfully.");
    }
}
