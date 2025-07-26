package com.scopely.assignment.PromotionRuleEngineMicroservice.metrics;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of the MetricsService interface.
 * Tracks performance and result metrics for rule evaluations.
 */
@Service
public class MetricsServiceImpl implements MetricsService {

    // Total number of evaluation requests processed
    private final AtomicInteger totalEvaluations = new AtomicInteger();

    // Total number of successful rule matches (hits)
    private final AtomicInteger hits = new AtomicInteger();

    // Total number of rule misses (no rule matched)
    private final AtomicInteger misses = new AtomicInteger();

    // Cumulative latency in milliseconds across all evaluations
    private final AtomicLong totalLatency = new AtomicLong();

    /**
     * Records one evaluation attempt.
     *
     * @param isHit     whether a rule matched for the request
     * @param latencyMs how long the evaluation took (in milliseconds)
     */
    @Override
    public void recordEvaluation(boolean isHit, long latencyMs) {
        totalEvaluations.incrementAndGet();           // Increment total count
        totalLatency.addAndGet(latencyMs);            // Add latency to running total
        if (isHit) hits.incrementAndGet();            // Track hit
        else misses.incrementAndGet();                // Track miss
    }

    /**
     * @return total number of evaluations recorded
     */
    @Override
    public int getTotalEvaluations() {
        return totalEvaluations.get();
    }

    /**
     * @return total number of evaluations where a rule was matched
     */
    @Override
    public int getHits() {
        return hits.get();
    }

    /**
     * @return total number of evaluations where no rule was matched
     */
    @Override
    public int getMisses() {
        return misses.get();
    }

    /**
     * @return average latency per evaluation (in milliseconds)
     */
    @Override
    public double getAverageLatency() {
        int total = totalEvaluations.get();
        return total == 0 ? 0 : (double) totalLatency.get() / total;
    }
}
