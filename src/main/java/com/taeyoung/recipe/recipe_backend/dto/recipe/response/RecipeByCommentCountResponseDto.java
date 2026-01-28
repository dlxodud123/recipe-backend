package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeByCommentCountResponseDto {
    private Long id;
    private String title;
    private String imgUrl;
    private Long commentCount;

    public static RecipeByCommentCountResponseDto from(Recipe recipe) {
        return new RecipeByCommentCountResponseDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getImageUrl(),
                (long) recipe.getComments().size()
        );
    }
}
