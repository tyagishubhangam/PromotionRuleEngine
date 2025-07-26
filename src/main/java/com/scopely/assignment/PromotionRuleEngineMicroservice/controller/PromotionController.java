package com.scopely.assignment.PromotionRuleEngineMicroservice.controller;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.MetricsResponse;
import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerPromotionResponse;
import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.metrics.MetricsService;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.Condition;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;
import com.scopely.assignment.PromotionRuleEngineMicroservice.service.RuleEngineService;
import com.scopely.assignment.PromotionRuleEngineMicroservice.service.RulesLoaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Promotion Rule Engine", description = "APIs for evaluating and managing promotion rules")
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class PromotionController {

    // Injected dependencies
    private final RuleEngineService ruleEngineService;
    private final RulesLoaderService rulesLoaderService;
    private final MetricsService metricsService;

    /**
     * Evaluate a player request and return the matching promotion (if any),
     * or a suggestion for what the player could do to become eligible.
     *
     * @param playerRequest the incoming player profile request
     * @return 200 OK with promotion or suggestion, or 204 if not found
     */
    @Operation(summary = "Get promotion for player", description = "Evaluates player attributes and returns matching promotion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promotion matched and returned", content = @Content(schema = @Schema(implementation = PlayerPromotionResponse.class))),
            @ApiResponse(responseCode = "204", description = "No matching promotion found", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content()),
    })
    @PostMapping("/promotion")
    public ResponseEntity<PlayerPromotionResponse> getPlayerPromotion(@Valid @RequestBody PlayerRequest playerRequest) {

        // Start latency measurement
        long start = System.currentTimeMillis();

        // Run rule evaluation
        PromotionPayload result = ruleEngineService.evaluate(playerRequest);

        // Measure time taken
        long latency = System.currentTimeMillis() - start;

        // Track evaluation metrics
        boolean hit = result != null;
        metricsService.recordEvaluation(hit, latency);

        // If no rule matched, return a helpful suggestion
        if (result == null) {
            String suggestion = ruleEngineService.suggestClosestRule(playerRequest);
            return ResponseEntity.ok(PlayerPromotionResponse.builder()
                    .promotion(null)
                    .suggestion(suggestion)
                    .build());
        }

        // Return successful promotion match
        return ResponseEntity.ok(PlayerPromotionResponse.builder()
                .promotion(result)
                .suggestion(null)
                .build());
    }

    /**
     * Expose internal service metrics (hits, misses, latency, total).
     *
     * @return metrics object as JSON
     */
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

    /**
     * Reload the YAML file containing promotion rules from disk.
     * Useful when updating the rules at runtime without a restart.
     *
     * @return confirmation message
     */
    @Operation(summary = "Reload rules from YAML", description = "Reloads rules.yaml at runtime without restarting the app")
    @PostMapping("/reloadRules")
    public ResponseEntity<String> reloadRules() {
        rulesLoaderService.loadRules();
        return ResponseEntity.ok("Rules reloaded successfully.");
    }

    /**
     * Fetch all promotion rules, with optional filtering by country.
     * If no country is provided, returns the complete rule set.
     *
     * @param country optional query param to filter rules by country code
     * @return list of matching promotion rules
     */
    @GetMapping("/rules")
    @Operation(summary = "Get all loaded promotion rules", description = "Returns promotion rules, optionally filtered by country")
    public ResponseEntity<List<PromotionRule>> getRulesByCountry(
            @RequestParam(required = false) String country) {

        List<PromotionRule> allRules = rulesLoaderService.getRules();
        List<PromotionRule> filteredRules = new ArrayList<>();

        // No filter – return all rules
        if (country == null || country.isBlank()) {
            return ResponseEntity.ok(allRules);
        }

        // Filter by matching country
        for (PromotionRule rule : allRules) {
            Condition condition = rule.getConditions();
            if (condition.getCountry() == null || condition.getCountry().equalsIgnoreCase(country)) {
                filteredRules.add(rule);
            }
        }

        return ResponseEntity.ok(filteredRules);
    }
}
