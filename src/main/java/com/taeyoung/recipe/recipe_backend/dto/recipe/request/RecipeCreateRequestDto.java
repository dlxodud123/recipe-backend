package com.taeyoung.recipe.recipe_backend.dto.recipe.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeCreateRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String subTitle;
    @NotBlank
    private String description;
    @NotBlank
    private String serving;
    @NotBlank
    private Long categoryId;

    @NotBlank
    private List<String> ingredients;
    @NotBlank
    private List<String> seasonings;
    @NotBlank
    private List<String> steps;
}
