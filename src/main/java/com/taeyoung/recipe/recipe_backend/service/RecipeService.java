package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.recipe.*;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByIngredientSearchResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.*;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateRecipeItemException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateRecipeTitleException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.CategoryRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeQueryRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeQueryRepository recipeQueryRepository;

    // 레시피 생성
    public Recipe save(RecipeCreateRequestDto dto, String imageUrl, Long userId) {
        // 멤버 조회
        Member member = memberRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        // title 중복 체크
        if (recipeRepository.existsByTitle(dto.getTitle())) {
            throw new DuplicateRecipeTitleException("이미 존재하는 요리 제목입니다.");
        }

        // 카테고리 조회
        Category category = categoryRepository.findByCategoryOrder(dto.getCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("카테고리가 존재하지 않습니다."));

        // 재료 및 양념 중복 체크
        validateNoDuplicate(dto);

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






    public RecipeByIdResponseDto getRecipeById(long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new EntityNotFoundException("레시피가 존재하지 않습니다."));

        recipe.increaseViewCount();

        return RecipeByIdResponseDto.from(recipe);
    }








    // 최신 레시피 조회
    @Transactional(readOnly = true)
    public List<RecipeRecentResponseDto> getRecentRecipe() {
        List<Recipe> recipes = recipeRepository.findTop5ByOrderByCreatedAtDesc();

        return recipes.stream()
            .map(RecipeRecentResponseDto::from)
            .toList();
    }
    // 조회수 TOP 조회(20개)
    @Transactional(readOnly = true)
    public List<RecipeByViewCountResponseDto> getTop20ByViewCount() {
        List<Recipe> recipes = recipeRepository.findTop20ByOrderByViewCountDescCreatedAtDesc();

        return recipes.stream()
            .map(RecipeByViewCountResponseDto::from)
            .toList();
    }
    // 댓글 랭킹 조회(5개)
    @Transactional(readOnly = true)
    public List<RecipeByCommentCountResponseDto> getTop20ByCommentCount() {
        List<Recipe> recipes = recipeRepository.findTop5ByOrderByCommentCountDesc();

        return recipes.stream()
                .map(RecipeByCommentCountResponseDto::from)
                .toList();
    }

    // 재료 활용
    public List<RecipeByIngredientSearchResponseDto> searchRecipes(List<String> includeIngredients, List<String> excludeIngredients) {
        if ((includeIngredients == null || includeIngredients.isEmpty())
                && (excludeIngredients == null || excludeIngredients.isEmpty())) {
            throw new IllegalArgumentException("재료나 제외할 재료를 입력하세요.");
        }

        long includeCount = includeIngredients != null ? includeIngredients.size() : 0;

        List<Recipe> recipes = recipeRepository.findByIngredients(
            includeIngredients,
            excludeIngredients != null ? excludeIngredients : List.of(),
            includeCount
        );

        return recipes.stream()
            .map(recipe -> RecipeByIngredientSearchResponseDto.from(recipe, excludeIngredients))
            .toList();
    }






    @Transactional(readOnly = true)
    public Page<RecipeByCategoryResponseDto> getRecipeByCategory(String categoryName, String keyword, Pageable pageable) {
        Category category = categoryRepository.findByName(categoryName)
            .orElseThrow(() -> new EntityNotFoundException("카테고리가 존재하지 않습니다."));

        Page<Recipe> recipes;
        if (keyword == null || keyword.isBlank()) {
            recipes = recipeRepository.findAllByCategoryId(category.getId(), pageable);
        } else {
            recipes = recipeRepository.findByCategoryIdAndTitleContaining(category.getId(), keyword, pageable);
        }

        return recipes.map(recipe ->
                new RecipeByCategoryResponseDto(
                        recipe.getId(),
                        recipe.getTitle(),
                        recipe.getSubTitle(),
                        recipe.getImageUrl()
                )
        );
    }
    @Transactional(readOnly = true)
    public List<RecipeBySearchResponseDto> searchByKeyword(String keyword) {
        return recipeQueryRepository.searchByKeyword(keyword);
    }









    // 재료, 양념 중복 체크 메서드
    private void validateNoDuplicate(RecipeCreateRequestDto dto) {

        // 재료 내부 중복 체크
        Set<String> ingredientSet = new HashSet<>();
        for (String raw : dto.getIngredients()) {
            if (raw == null) continue;
            String value = raw.trim().replaceAll("\\s+", " ").toLowerCase();
            if (!ingredientSet.add(value)) {
                throw new DuplicateRecipeItemException("재료에 중복된 항목이 있습니다.");
            }
        }
        // 양념 내부 중복 체크
        Set<String> seasoningSet = new HashSet<>();
        for (String raw : dto.getSeasonings()) {
            if (raw == null) continue;
            String value = raw.trim().replaceAll("\\s+", " ").toLowerCase();
            if (!seasoningSet.add(value)) {
                throw new DuplicateRecipeItemException("양념에 중복된 항목이 있습니다.");
            }
        }
    }
}
