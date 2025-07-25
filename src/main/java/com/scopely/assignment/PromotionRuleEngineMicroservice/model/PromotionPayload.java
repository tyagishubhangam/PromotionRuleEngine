package com.scopely.assignment.PromotionRuleEngineMicroservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "Represents the promotion details shown to the player")
@Data
public class PromotionPayload {
    @Schema(description = "Title of the promotion", example = "Comeback Bonus")
    private String title;

    @Schema(description = "Reward offered in the promotion", example = "100 coins")
    private String reward;
}
