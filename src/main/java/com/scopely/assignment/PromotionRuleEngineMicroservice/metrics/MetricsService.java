package com.scopely.assignment.PromotionRuleEngineMicroservice.metrics;

/**
 * Service interface for collecting and exposing metrics
 * related to rule evaluation performance and results.
 */
public interface MetricsService {

    /**
     * Records the result of a rule evaluation.
     *
     * @param isHit     whether a matching promotion rule was found (true = hit, false = miss)
     * @param latencyMs the time it took to evaluate the rule in milliseconds
     */
    void recordEvaluation(boolean isHit, long latencyMs);

    /**
     * @return the total number of evaluation requests made
     */
    int getTotalEvaluations();

    /**
     * @return the number of evaluations that resulted in a matching rule (hits)
     */
    int getHits();

    /**
     * @return the number of evaluations that resulted in no matching rule (misses)
     */
    int getMisses();

    /**
     * @return the average latency (in milliseconds) of evaluations
     */
    double getAverageLatency();
}
