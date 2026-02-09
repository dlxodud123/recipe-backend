package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    Optional<Category> findByCategoryOrder(Long categoryOrder);
}
