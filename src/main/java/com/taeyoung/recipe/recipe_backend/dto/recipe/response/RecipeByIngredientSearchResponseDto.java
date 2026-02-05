package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecipeByIngredientSearchResponseDto {
    private Long id;
    private String title;
    private String subTitle;
    private Long viewCount;
    private Long commentCount;
    private String imgUrl;

    private List<String> includeIngredients; // 포함 재료
    private List<String> excludeIngredients; // 제외 재료

    public static RecipeByIngredientSearchResponseDto from(Recipe recipe, List<String> excludeIngredients) {
        return new RecipeByIngredientSearchResponseDto(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getSubTitle(),
            recipe.getViewCount(),
            recipe.getComments() != null ? (long) recipe.getComments().size() : 0L,
            recipe.getImageUrl(),
            recipe.getIngredients() != null
                ? recipe.getIngredients().stream().map(i -> i.getName()).toList()
                : List.of(),
            excludeIngredients != null ? excludeIngredients : List.of()
        );
    }
}
