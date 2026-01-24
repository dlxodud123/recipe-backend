package com.taeyoung.recipe.recipe_backend.dto.recipe.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecipeByIdResponseDto {
    private Long id;
    private String title;
    private String subTitle;
    private String description;

    private Integer serving;

    private String imgUrl;

    private String categoryName;

    private List<RecipeIngredientResponseDto> ingredients;
    private List<RecipeSeasoningResponseDto> seasonings;
    private List<RecipeStepResponseDto> steps;

    public static RecipeByIdResponseDto from(Recipe recipe) {
        return new RecipeByIdResponseDto(
            recipe.getId(),
            recipe.getTitle(),
            recipe.getSubTitle(),
            recipe.getDescription(),
            recipe.getServing(),
            recipe.getImageUrl(),
//            recipe.getCategory().getName(),
            recipe.getCategory() != null ? recipe.getCategory().getName() : "",
//            recipe.getIngredients().stream()
//                    .map(RecipeIngredientResponseDto::from)
//                    .toList(),
            recipe.getIngredients() != null
                    ? recipe.getIngredients().stream().map(RecipeIngredientResponseDto::from).toList()
                    : List.of(),
//            recipe.getSeasonings().stream()
//                    .map(RecipeSeasoningResponseDto::from)
//                    .toList(),
            recipe.getSeasonings() != null
                    ? recipe.getSeasonings().stream().map(RecipeSeasoningResponseDto::from).toList()
                    : List.of(),
//            recipe.getSteps().stream()
//                    .map(RecipeStepResponseDto::from)
//                    .toList()
            recipe.getSteps() != null
                    ? recipe.getSteps().stream().map(RecipeStepResponseDto::from).toList()
                    : List.of()
        );
    }
}
