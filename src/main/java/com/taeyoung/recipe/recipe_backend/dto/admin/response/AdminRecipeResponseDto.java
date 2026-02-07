package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminRecipeResponseDto {
    private Long id;
    private String title;
    private String category;
    private String authorName;
    
    private final Long ingredientCount;
    private final Long seasoningCount;
    private final Long stepCount;
}
