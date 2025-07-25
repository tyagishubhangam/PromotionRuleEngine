package com.scopely.assignment.PromotionRuleEngineMicroservice.metrics;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetricsServiceImpl implements MetricsService {
    private final AtomicInteger totalEvaluations = new AtomicInteger();
    private final AtomicInteger hits = new AtomicInteger();
    private final AtomicInteger misses = new AtomicInteger();
    private final AtomicLong totalLatency = new AtomicLong();

    @Override
    public void recordEvaluation(boolean isHit, long latencyMs) {
        totalEvaluations.incrementAndGet();
        totalLatency.addAndGet(latencyMs);
        if (isHit) hits.incrementAndGet();
        else misses.incrementAndGet();
    }

    @Override
    public int getTotalEvaluations() {
        return totalEvaluations.get();
    }

    @Override
    public int getHits() {
        return hits.get();
    }

    @Override
    public int getMisses() {
        return misses.get();
    }

    @Override
    public double getAverageLatency() {
        int total = totalEvaluations.get();
        return total == 0 ? 0 : (double) totalLatency.get() / total;
    }
}
