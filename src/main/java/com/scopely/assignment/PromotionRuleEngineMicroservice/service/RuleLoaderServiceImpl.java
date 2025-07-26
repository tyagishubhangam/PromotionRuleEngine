package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.Condition;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class RuleLoaderServiceImpl implements RulesLoaderService {

    private List<PromotionRule> rules = Collections.emptyList();

    // ✅ Added: Nested map for efficient access — country -> abBucket -> rules
    private Map<String, Map<String, List<PromotionRule>>> rulesByCountryAndBucket = new HashMap<>();

    @PostConstruct
    public void loadRulesOnStartup() {
        loadRules();
    }

    @Override
    public void loadRules() {
        try {
            // ✅ Modern Jackson configuration
            ObjectMapper objectMapper = JsonMapper.builder(new YAMLFactory())
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                    .build();
            objectMapper.registerModule(new JavaTimeModule());

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rules.yaml");
            rules = objectMapper.readValue(inputStream, new TypeReference<List<PromotionRule>>() {});

            // ✅ Clear and rebuild index
            rulesByCountryAndBucket = new HashMap<>();

            // ✅ Build the nested index
            for (PromotionRule rule : rules) {
                Condition condition = rule.getConditions();
                if (condition == null) continue;

                String country = condition.getCountry();
                String abBucket = condition.getAbBucket();
                if (country == null || abBucket == null) continue;

                String countryKey = country.toUpperCase();
                String bucketKey = abBucket.toUpperCase();

                rulesByCountryAndBucket
                        .computeIfAbsent(countryKey, c -> new HashMap<>())
                        .computeIfAbsent(bucketKey, b -> new ArrayList<>())
                        .add(rule);
            }

            log.info("Loaded {} rules with indexing by country and bucket", rules.size());

        } catch (Exception e) {
            log.error("Failed to load rules: {}", e.getMessage(), e);
        }
    }

    @Override
    public List<PromotionRule> getRules() {
        return rules;
    }

    // ✅ New method to get rules by country + bucket
    public List<PromotionRule> getRulesByCountryAndBucket(String country, String abBucket) {
        if (country == null || abBucket == null) {
            return rules; // fallback to all rules
        }

        Map<String, List<PromotionRule>> bucketMap =
                rulesByCountryAndBucket.getOrDefault(country.toUpperCase(), Collections.emptyMap());

        return bucketMap.getOrDefault(abBucket.toUpperCase(), Collections.emptyList());
    }
}
