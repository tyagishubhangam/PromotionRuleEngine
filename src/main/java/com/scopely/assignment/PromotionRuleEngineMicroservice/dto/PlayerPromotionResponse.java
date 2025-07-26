package com.scopely.assignment.PromotionRuleEngineMicroservice.dto;

import com.scopely.assignment.PromotionRuleEngineMicroservice.model.PromotionPayload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Response object for the /promotion API.
 *
 * This DTO encapsulates the result of a rule evaluation for a given player,
 * including the matched promotion (if any) and a fallback suggestion if no match is found.
 */
@Data
@Builder
@AllArgsConstructor
public class PlayerPromotionResponse {

    /**
     * The promotion matched for the player, if any.
     * Will be null if no rule matched.
     */
    @Schema(description = "The matched promotion details, null if no rule matched")
    private PromotionPayload promotion;

    /**
     * A helpful suggestion string indicating what the player can do to qualify,
     * or a fallback message if no suggestion applies.
     */
    @Schema(description = "Suggestion message shown when no promotion matched")
    private String suggestion;
}
