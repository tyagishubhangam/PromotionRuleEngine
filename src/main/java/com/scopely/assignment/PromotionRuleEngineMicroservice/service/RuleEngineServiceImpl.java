package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.scopely.assignment.PromotionRuleEngineMicroservice.dto.PlayerRequest;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.Condition;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleEngineServiceImpl implements RuleEngineService {

    private final RulesLoaderService rulesLoaderService;

    @Override
    public PromotionPayload evaluate(PlayerRequest player) {
        for(PromotionRule rule : rulesLoaderService.getRules() ){
            Condition condition = rule.getConditions();
            if (condition.getMinLevel() != null && player.getLevel() < condition.getMinLevel()) { continue;}
            if (condition.getMaxLevel() != null && player.getLevel() > condition.getMaxLevel()) { continue;}
            if (condition.getSpendTier() != null && !condition.getSpendTier().equalsIgnoreCase(player.getSpendTier())) { continue;}
            if (condition.getCountry() != null && !condition.getCountry().equalsIgnoreCase(player.getCountry())) { continue;}
            if (condition.getDaysSinceLastPurchase() != null && player.getDaysSinceLastPurchase() != condition.getDaysSinceLastPurchase()) { continue;}

            return rule.getPromotion();
        }
        return null;
    }

    @Override
    public String suggestClosestRule(PlayerRequest player) {
        for (PromotionRule rule : rulesLoaderService.getRules()) {
            Condition c = rule.getConditions();

            if (c.getMinLevel() != null && player.getLevel() < c.getMinLevel()) {
                return "Level up to " + c.getMinLevel() + "+ to unlock " + rule.getPromotion().getTitle();
            }

            if (c.getSpendTier() != null && !c.getSpendTier().equalsIgnoreCase(player.getSpendTier())) {
                return "Upgrade to " + c.getSpendTier() + " spend tier to unlock " + rule.getPromotion().getTitle();
            }

            if (c.getCountry() != null && !c.getCountry().equalsIgnoreCase(player.getCountry())) {
                return "Play from " + c.getCountry() + " to qualify for " + rule.getPromotion().getTitle();
            }

            if (c.getDaysSinceLastPurchase() != null &&
                    !c.getDaysSinceLastPurchase().equals(player.getDaysSinceLastPurchase())) {
                return "Wait " + c.getDaysSinceLastPurchase() + " days since last purchase to qualify for " + rule.getPromotion().getTitle();
            }
        }

        return "No matching promotion found. Keep playing to unlock exciting rewards!";
    }
}
