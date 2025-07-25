package com.scopely.assignment.PromotionRuleEngineMicroservice.metrics;

import org.springframework.stereotype.Service;


public interface MetricsService {

    void recordEvaluation(boolean isHit, long latencyMs);
    int getTotalEvaluations();
    int getHits();
    int getMisses();
    double getAverageLatency();
}
