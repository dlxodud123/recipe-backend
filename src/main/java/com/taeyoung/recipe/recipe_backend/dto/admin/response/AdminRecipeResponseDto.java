package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AdminRecipeResponseDto {
    private Long id;
    private String title;
    private String category;
    private String authorName;
    private String created;

    public AdminRecipeResponseDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.category = recipe.getCategory().getName();
        this.authorName = recipe.getMember().getName();
        this.created = recipe.getCreatedAt() != null
                ? recipe.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
    }
}
