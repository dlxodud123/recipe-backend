package com.taeyoung.recipe.recipe_backend.repository.study;

import com.taeyoung.recipe.recipe_backend.domain.study.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
