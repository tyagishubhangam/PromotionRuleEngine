package com.scopely.assignment.PromotionRuleEngineMicroservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration class.
 * This sets up the API documentation metadata for Swagger UI.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Defines the OpenAPI documentation bean for SpringDoc.
     *
     * This sets the basic information such as:
     * - Title of the API
     * - Version
     * - Description
     *
     * @return OpenAPI instance for Swagger UI documentation
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Promotion Rule Engine Microservice")
                        .version("1.0.0")
                        .description("Backend engine for matching players with dynamic promotions")
                );
    }
}
