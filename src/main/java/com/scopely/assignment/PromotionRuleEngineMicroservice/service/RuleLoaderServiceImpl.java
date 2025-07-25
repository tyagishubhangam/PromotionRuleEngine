package com.scopely.assignment.PromotionRuleEngineMicroservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionRule;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RuleLoaderServiceImpl implements RulesLoaderService {
    private List<PromotionRule> rules = Collections.emptyList();

    @PostConstruct
    public void loadRulesOnStartup() {
        loadRules();
    }
    @Override
    public void loadRules() {
        try{
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            objectMapper.registerModule(new JavaTimeModule()); // ✅ Required for LocalDateTime
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rules.yaml");
            rules = objectMapper.readValue(inputStream, new TypeReference<List<PromotionRule>>() {});
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
    @Override
    public List<PromotionRule> getRules() {
        return rules;
    }
}
