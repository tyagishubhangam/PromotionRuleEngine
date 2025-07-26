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

    // 🔁 Stores all loaded rules (raw list)
    private List<PromotionRule> rules = Collections.emptyList();

    // ⚡ Optimized index: country -> abBucket -> List<PromotionRule>
    private Map<String, Map<String, List<PromotionRule>>> rulesByCountryAndBucket = new HashMap<>();

    /**
     * Loads rules automatically on service startup
     */
    @PostConstruct
    public void loadRulesOnStartup() {
        loadRules();
    }

    /**
     * Loads rules from the YAML file into memory,
     * builds fast lookup map for country + A/B bucket combinations
     */
    @Override
    public void loadRules() {
        try {
            // 📦 Create an ObjectMapper with YAML + LocalDateTime support
            ObjectMapper objectMapper = JsonMapper.builder(new YAMLFactory())
                    .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS) // Accept enums like "low" or "LOW"
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore extra fields in YAML
                    .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE) // Prevent unwanted date shifting
                    .build();
            objectMapper.registerModule(new JavaTimeModule()); // Support for LocalDateTime

            // 📄 Load the rules.yaml file from resources
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rules.yaml");

            // 🧾 Deserialize YAML into Java objects
            rules = objectMapper.readValue(inputStream, new TypeReference<List<PromotionRule>>() {});

            // ♻️ Rebuild the country + bucket lookup map
            rulesByCountryAndBucket = new HashMap<>();

            for (PromotionRule rule : rules) {
                Condition condition = rule.getConditions();
                if (condition == null) continue;

                String country = condition.getCountry();
                String abBucket = condition.getAbBucket();
                if (country == null || abBucket == null) continue;

                // 📌 Normalize to uppercase for consistent matching
                String countryKey = country.toUpperCase();
                String bucketKey = abBucket.toUpperCase();

                rulesByCountryAndBucket
                        .computeIfAbsent(countryKey, c -> new HashMap<>())
                        .computeIfAbsent(bucketKey, b -> new ArrayList<>())
                        .add(rule);
            }

            log.info("✅ Loaded {} rules and indexed by country and A/B bucket", rules.size());

        } catch (Exception e) {
            log.error("❌ Failed to load rules: {}", e.getMessage(), e);
        }
    }

    /**
     * Returns all loaded rules (raw list from YAML)
     */
    @Override
    public List<PromotionRule> getRules() {
        return rules;
    }

    /**
     * Returns rules filtered by both country and A/B bucket.
     * Falls back to returning all rules if any key is missing.
     *
     * @param country   Country code (e.g., "IN")
     * @param abBucket  A/B test bucket (e.g., "A")
     * @return List of matching rules
     */
    public List<PromotionRule> getRulesByCountryAndBucket(String country, String abBucket) {
        if (country == null || abBucket == null) {
            return rules; // fallback: return all rules (non-optimized)
        }

        Map<String, List<PromotionRule>> bucketMap =
                rulesByCountryAndBucket.getOrDefault(country.toUpperCase(), Collections.emptyMap());

        return bucketMap.getOrDefault(abBucket.toUpperCase(), Collections.emptyList());
    }
}
