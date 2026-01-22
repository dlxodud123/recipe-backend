package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Recipe save(RecipeCreateRequestDto request) {
        Recipe recipe = new Recipe();
//        recipe.setTitle(request.getTitle());
//        recipe.setSubTitle(request.getSubTitle());
//        recipe.setDescription(request.getDescription());
//        recipe.setServing(request.getServing());
//        recipe.setCategory(request.getCategory());
//        recipe.setIngredients(request.getIngredients());
//        recipe.setSeasonings(request.getSeasonings());
//        recipe.setSteps(request.getSteps());
//        recipe.setImageUrl(request.getImageUrl());

        return recipeRepository.save(recipe);
    }
}
