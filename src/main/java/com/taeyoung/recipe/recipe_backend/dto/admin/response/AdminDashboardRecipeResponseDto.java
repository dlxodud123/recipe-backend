package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminDashboardRecipeResponseDto {

    private final Long recipeId;
    private final String title;
    private final String createdAt;
    private final String authorName;

    public static AdminDashboardRecipeResponseDto from(Recipe recipe) {
        return new AdminDashboardRecipeResponseDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getCreatedAt().toLocalDate().toString(),
                recipe.getMember().getName()
        );
    }
}

