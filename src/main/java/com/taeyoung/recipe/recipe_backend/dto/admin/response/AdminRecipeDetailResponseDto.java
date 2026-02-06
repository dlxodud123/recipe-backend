package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Getter
public class AdminRecipeDetailResponseDto {
    private Long id;
    private String title;
    private String subTitle;
    private String description;
    private String categoryName;
    private String authorName;
    private int serving;
    private String ingredients;
    private String seasonings;
    private String steps;
    private String created;
    private int viewCount;
    private int commentCount;

    public AdminRecipeDetailResponseDto(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.subTitle = recipe.getSubTitle();
        this.description = recipe.getDescription();
        this.categoryName = recipe.getCategory().getName();
        this.authorName = recipe.getMember().getName();
        this.serving = recipe.getServing();

        this.ingredients = recipe.getIngredients().stream()
                .map(ri -> ri.getName())
                .collect(Collectors.joining(" / "));
        this.seasonings = recipe.getSeasonings().stream()
                .map(ri -> ri.getName())
                .collect(Collectors.joining(" / "));
        this.steps = recipe.getSteps().stream()
                .map(ri -> ri.getContent())
                .collect(Collectors.joining(" / "));

        this.created = recipe.getCreatedAt() != null
                ? recipe.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
        this.viewCount = recipe.getViewCount().intValue();
        this.commentCount = recipe.getComments().size();
    }
}
