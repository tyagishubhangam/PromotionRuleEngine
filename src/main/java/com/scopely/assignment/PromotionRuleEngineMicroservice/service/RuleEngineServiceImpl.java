package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.Condition;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleEngineServiceImpl implements RuleEngineService {

    private final RulesLoaderService rulesLoaderService;

    @Override
    public PromotionPayload evaluate(PlayerRequest player) {

        PromotionRule bestMatch = null;
        int highestPriority = Integer.MIN_VALUE;

        for (PromotionRule rule : rulesLoaderService.getRulesByCountryAndBucket(
                player.getCountry(), player.getAbBucket())){
            // 🔧 Time window validation
            if (rule.getStartTime() != null && LocalDateTime.now().isBefore(rule.getStartTime())) continue;
            if (rule.getEndTime() != null && LocalDateTime.now().isAfter(rule.getEndTime())) continue;

            Condition condition = rule.getConditions();
            if (condition.getMinLevel() != null && player.getLevel() < condition.getMinLevel()) { continue;}
            if (condition.getMaxLevel() != null && player.getLevel() > condition.getMaxLevel()) { continue;}
            if (condition.getSpendTier() != null && !condition.getSpendTier().equals(player.getSpendTier())) { continue;}
            if (condition.getCountry() != null && !condition.getCountry().equalsIgnoreCase(player.getCountry())) { continue;}
            if (condition.getDaysSinceLastPurchase() != null && player.getDaysSinceLastPurchase() != condition.getDaysSinceLastPurchase()) { continue;}

            // 🔧 A/B bucket check
            if (condition.getAbBucket() != null && !condition.getAbBucket().equalsIgnoreCase(player.getAbBucket())) continue;


            // 🎯 If rule matches, check priority
            int priority = rule.getPriority() != null ? rule.getPriority() : 0;
            if (priority > highestPriority) {
                highestPriority = priority;
                bestMatch = rule;
            }
        }

        if (bestMatch != null) {
            log.info("Matched rule by priority: {}", bestMatch.getId());
            return bestMatch.getPromotion();
        }
        return null;
    }

    @Override
    public String suggestClosestRule(PlayerRequest player) {
        for (PromotionRule rule : rulesLoaderService.getRulesByCountryAndBucket(
                player.getCountry(), player.getAbBucket())) {
            Condition c = rule.getConditions();
            int mismatches = 0;
            String reason = "";

            if (c.getMinLevel() != null && player.getLevel() < c.getMinLevel()) {
                mismatches++;
                reason = "Level up to " + c.getMinLevel() + "+ to unlock " + rule.getPromotion().getTitle();
            }

            if (c.getSpendTier() != null && !c.getSpendTier().equals(player.getSpendTier())) {
                mismatches++;
                reason = "Upgrade to " + c.getSpendTier() + " spend tier to unlock " + rule.getPromotion().getTitle();
            }

            if (c.getCountry() != null && !c.getCountry().equalsIgnoreCase(player.getCountry())) {
                mismatches++;
                reason = "Play from " + c.getCountry() + " to qualify for " + rule.getPromotion().getTitle();
            }

            if (c.getDaysSinceLastPurchase() != null &&
                    !c.getDaysSinceLastPurchase().equals(player.getDaysSinceLastPurchase())) {
                mismatches++;
                reason = "Wait " + c.getDaysSinceLastPurchase() + " days since last purchase to qualify for " + rule.getPromotion().getTitle();
            }

            if (c.getAbBucket() != null && !c.getAbBucket().equalsIgnoreCase(player.getAbBucket())) {
                mismatches++;
                reason = "Assigned to " + c.getAbBucket() + " bucket to qualify for " + rule.getPromotion().getTitle();
            }

            if (mismatches == 1) {
                return reason;
            }
        }

        return "No matching promotion found. Keep playing to unlock exciting rewards!";
    }

}
