package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminRecipeResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByCommentCountResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeBySearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // title 중복 체크
    boolean existsByTitle(String title);



    // 최근 조회(5개)
    List<Recipe> findTop5ByOrderByCreatedAtDesc();

    // 조회수 TOP(20개, 같을 경우 최신 data 불러옴)
    List<Recipe> findTop20ByOrderByViewCountDescCreatedAtDesc();

    // 댓글 랭킹 조회(5개)
    List<Recipe> findTop5ByOrderByCommentCountDesc();






    Page<Recipe> findAllByCategoryId(Long categoryId, Pageable pageable);
    Page<Recipe> findByCategoryIdAndTitleContaining(Long categoryId, String keyword, Pageable pageable);

//    @Query("""
//        SELECT new com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeBySearchResponseDto(
//            r.category.name
//        )
//        FROM Recipe r
//        WHERE r.title LIKE %:keyword%
//           OR r.description LIKE %:keyword%
//    """)
//    List<RecipeBySearchResponseDto> searchByKeyword(@Param("keyword") String keyword);







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

    // admin(전체 레시피)(fetch join)
    @Query("""
        select new com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminRecipeResponseDto(
            r.id,
            r.title,
            c.name,
            m.name,
            count(distinct i.id),
            count(distinct s.id),
            count(distinct st.id)
        )
        from Recipe r
        join r.category c
        join r.member m
        left join r.ingredients i
        left join r.seasonings s
        left join r.steps st
        group by r.id, r.title, c.name, m.name
    """)
    Page<AdminRecipeResponseDto> findAdminRecipes(Pageable pageable);
}
