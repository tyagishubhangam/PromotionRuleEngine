package com.scopely.assignment.PromotionRuleEngineMicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Promotion Rule Engine Microservice")
                .version("1.0.0")
                .description("Backend engine for matching players with dynamic promotions")
        );

    }
}
