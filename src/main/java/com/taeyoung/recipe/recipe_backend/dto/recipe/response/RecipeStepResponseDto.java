package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.RecipeIngredient;
import com.taeyoung.recipe.recipe_backend.domain.recipe.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeStepResponseDto {
    private String content;
    private int order;

    public static RecipeStepResponseDto from(RecipeStep entity) {
        return new RecipeStepResponseDto(
            entity.getContent(),
            entity.getStepOrder()
        );
    }
}
