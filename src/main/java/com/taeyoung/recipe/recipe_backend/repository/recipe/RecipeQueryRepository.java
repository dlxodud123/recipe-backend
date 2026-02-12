package com.taeyoung.recipe.recipe_backend.repository.recipe;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.taeyoung.recipe.recipe_backend.domain.recipe.QRecipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeBySearchResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public RecipeQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<RecipeBySearchResponseDto> searchByKeyword(String keyword) {
        QRecipe recipe = QRecipe.recipe;

        return queryFactory
                .select(Projections.constructor(
                        RecipeBySearchResponseDto.class,
                        recipe.id,
                        recipe.title,
                        recipe.category.name
                ))
                .from(recipe)
                .where(recipe.title.containsIgnoreCase(keyword))
                .fetch();
    }
}
