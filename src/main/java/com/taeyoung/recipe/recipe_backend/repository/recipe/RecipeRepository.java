package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
