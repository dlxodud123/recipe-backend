package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByIngredientSearchResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.*;
import com.taeyoung.recipe.recipe_backend.service.RecipeService;
import com.taeyoung.recipe.recipe_backend.service.S3UploaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final S3UploaderService s3UploaderService;
    private final RecipeService recipeService;

    // 레시피 생성
    @PostMapping("/create")
    public Recipe createStudy(@Valid @RequestPart("recipe") RecipeCreateRequestDto recipeCreateRequestDto,
//                              @RequestPart("image") MultipartFile image,
                                            Authentication authentication) throws IOException {

        Long userId = ((CustomUser) authentication.getPrincipal()).getId();

//        if (image == null || image.isEmpty()) {
//            throw new IllegalArgumentException("이미지를 업로드해주세요");
//        }
//        String imageUrl = s3UploaderService.uploadFile(image);

        String imageUrl = "이미지";

        // 서버용
        return recipeService.save(recipeCreateRequestDto, imageUrl, userId);

        // 테스트용
//        return recipeService.save(recipeCreateRequestDto, imageUrl, 5L);
    }

    // 레시피 조회(카테고리)
    @GetMapping("/category")
    public Page<RecipeByCategoryResponseDto> getRecipeByCategory(
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(size = 15) Pageable pageable
    ) {
        return recipeService.getRecipeByCategory(categoryName, keyword, pageable);
    }

    // 상세 레시피 조회(id)
    @GetMapping("/detail/{recipeId}")
    public RecipeByIdResponseDto getRecipeById(@PathVariable Long recipeId) {

        return recipeService.getRecipeById(recipeId);
    }

    // 최신 레시피 조회(5개)
    @GetMapping("/recent")
    public List<RecipeRecentResponseDto> getRecentRecipe(){

        return recipeService.getRecentRecipe();
    }

    // 조회수 TOP 조회(20개)
    @GetMapping("/views")
    public List<RecipeByViewCountResponseDto> getViewRecipe(){

        return recipeService.getTop20ByViewCount();
    }

    // 댓글 랭킹 조회(5개)
    @GetMapping("/top-commented")
    public List<RecipeByCommentCountResponseDto> getTopCommentedRecipes() {

        return recipeService.getTop20ByCommentCount();
    }


    // 재료 활용
    @GetMapping("/ingredients/search")
    public List<RecipeByIngredientSearchResponseDto> searchRecipes(@RequestParam(required = false) List<String> ingredient,
                                                                   @RequestParam(required = false) List<String> exceptIngredient) {

        List<String> includeIngredients = ingredient != null ? ingredient : List.of();
        List<String> excludeIngredients = exceptIngredient != null ? exceptIngredient : List.of();

        return recipeService.searchRecipes(includeIngredients, excludeIngredients);
    }


    // 검색(header)
    @GetMapping("/search")
    public List<RecipeBySearchResponseDto> searchByKeyword(@RequestParam String keyword) {

        return recipeService.searchByKeyword(keyword);
    }
}
