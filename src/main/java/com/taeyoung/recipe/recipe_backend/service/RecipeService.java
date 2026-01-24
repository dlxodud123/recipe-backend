package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.recipe.*;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByCategoryResponseDto;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.CategoryRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    public Recipe save(RecipeCreateRequestDto dto, String imageUrl, Long userId) {
        // 멤버 조회
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        // 카테고리 조회
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리가 존재하지 않습니다."));

        // 기본정보 세팅
        Recipe recipe = new Recipe();
        recipe.setBasicInfo(
                dto.getTitle(),
                dto.getSubTitle(),
                dto.getDescription(),
                dto.getServing(),
                category,
                imageUrl
        );

        // recipe와 ingredient 연결(양방향)
        int ingredientOrder = 1;
        for(String name : dto.getIngredients()) {
            RecipeIngredient ingredient = new RecipeIngredient();
            // ingredient에 정보 세팅
            ingredient.setNameAndOrder(name, ingredientOrder++);

            recipe.addIngredient(ingredient);
        }
        // recipe와 seasoning 연결(양방향)
        int seasoningOrder = 1;
        for(String name : dto.getSeasonings()) {
            RecipeSeasoning seasoning = new RecipeSeasoning();
            // seasoning에 정보 세팅
            seasoning.setNameAndOrder(name, seasoningOrder++);

            recipe.addSeasoning(seasoning);
        }
        // recipe와 step 연결(양방향)
        int stepOrder = 1;
        for(String name : dto.getSteps()) {
            RecipeStep step = new RecipeStep();
            // step에 정보 세팅
            step.setNameAndOrder(name, stepOrder++);

            recipe.addStep(step);
        }

        // member와 recipe 연결(양방향)
        recipe.setMember(member);

        // 저장
        return recipeRepository.save(recipe);
    }

    public List<RecipeByCategoryResponseDto> getRecipeByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("카테고리가 존재하지 않습니다."));

        return recipeRepository.findAllByCategoryId(category.getId())
                .stream()
                .map(recipe -> new RecipeByCategoryResponseDto(
                        recipe.getTitle(),
                        recipe.getSubTitle(),
                        recipe.getImageUrl()
                ))
                .toList();
    }
}
