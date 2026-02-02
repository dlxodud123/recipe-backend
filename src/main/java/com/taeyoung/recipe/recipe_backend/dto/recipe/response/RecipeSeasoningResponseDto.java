package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.RecipeSeasoning;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeSeasoningResponseDto {
    private String name;
    private int order;

    public static RecipeSeasoningResponseDto from(RecipeSeasoning entity) {
        return new RecipeSeasoningResponseDto(
            entity.getName(),
            entity.getSeasoningOrder()
        );
    }
}
