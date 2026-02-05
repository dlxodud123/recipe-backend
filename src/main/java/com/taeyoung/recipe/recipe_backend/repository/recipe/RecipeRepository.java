package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByCommentCountResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeBySearchResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // title 중복 체크
    boolean existsByTitle(String title);

    // 조회
    List<Recipe> findAllByCategoryId(Long categoryId);
    List<Recipe> findByCategoryIdAndTitleContaining(Long categoryId, String title);

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

    // 포함 재료 포함 + 제외 재료 미포함 검색 (예시)
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i " +
            "WHERE i.name IN :includeIngredients " +
            "AND NOT EXISTS (SELECT 1 FROM r.ingredients e WHERE e.name IN :excludeIngredients) " +
            "GROUP BY r " +
            "HAVING COUNT(DISTINCT i.id) = :includeCount")
    List<Recipe> findByIngredients(
            @Param("includeIngredients") List<String> includeIngredients,
            @Param("excludeIngredients") List<String> excludeIngredients,
            @Param("includeCount") long includeCount
    );

    // 검색(header)
    @Query("""
        SELECT new com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeBySearchResponseDto(
            r.category.name
        )
        FROM Recipe r
        WHERE r.title LIKE %:keyword%
           OR r.description LIKE %:keyword%
    """)
    List<RecipeBySearchResponseDto> searchByKeyword(@Param("keyword") String keyword);
}
