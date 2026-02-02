package com.taeyoung.recipe.recipe_backend.dto.recipe.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Integer serving;
    @NotNull
    private Long categoryId;

    @NotEmpty
    private List<String> ingredients;
    @NotEmpty
    private List<String> seasonings;
    @NotEmpty
    private List<String> steps;
}
