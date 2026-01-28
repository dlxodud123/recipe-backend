package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // title 중복 체크
    boolean existsByTitle(String title);

    // 조회
    List<Recipe> findAllByCategoryId(Long categoryId);

    // 최근 조회(5개)
    List<Recipe> findTop5ByOrderByCreatedAtDesc();

    // 조회수 TOP(20개)
    List<Recipe> findTop20ByOrderByViewCountDesc();
}
