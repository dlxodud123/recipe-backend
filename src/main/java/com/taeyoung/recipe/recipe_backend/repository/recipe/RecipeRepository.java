package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByCommentCountResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // title 중복 체크
    boolean existsByTitle(String title);

    // 조회
    List<Recipe> findAllByCategoryId(Long categoryId);

    // 최근 조회(5개)
    List<Recipe> findTop5ByOrderByCreatedAtDesc();

    // 조회수 TOP(20개, 같을 경우 최신 data 불러옴)
    List<Recipe> findTop20ByOrderByViewCountDescCreatedAtDesc();

    // 댓글 랭킹 조회(5개)
    @Query("""
        SELECT new com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByCommentCountResponseDto(
            r.id,
            r.title,
            r.imageUrl,
            COUNT(c)
        )
        FROM Recipe r
        LEFT JOIN r.comments c
        GROUP BY r.id, r.title, r.imageUrl, r.createdAt
        ORDER BY COUNT(c) DESC, r.createdAt DESC
    """)
    List<RecipeByCommentCountResponseDto> findTop5ByCommentCount(Pageable pageable);
}
