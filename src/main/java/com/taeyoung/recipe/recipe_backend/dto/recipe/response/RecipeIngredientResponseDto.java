package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.RecipeIngredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeIngredientResponseDto {
    private String name;
    private int order;

    public static RecipeIngredientResponseDto from(RecipeIngredient entity) {
        return new RecipeIngredientResponseDto(
            entity.getName(),
            entity.getIngredientOrder()
        );
    }
}
