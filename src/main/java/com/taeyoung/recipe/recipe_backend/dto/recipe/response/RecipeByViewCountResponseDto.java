package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeByViewCountResponseDto {
    private Long id;
    private String title;
    private String imgUrl;

    public static RecipeByViewCountResponseDto from(Recipe recipe) {
        return new RecipeByViewCountResponseDto(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getImageUrl()
        );
    }
}
