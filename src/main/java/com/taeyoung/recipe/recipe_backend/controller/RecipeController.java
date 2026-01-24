package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.service.RecipeService;
import com.taeyoung.recipe.recipe_backend.service.S3UploaderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {

    private final S3UploaderService s3UploaderService;
    private final RecipeService recipeService;

    @PostMapping("/create")
    public ResponseEntity<?> createStudy(@RequestPart("image") MultipartFile image,
                                         @Valid @RequestPart("recipe") RecipeCreateRequestDto recipeCreateRequestDto,
                                         Authentication authentication) throws IOException {
//        Long userId = ((CustomUser) authentication.getPrincipal()).getId();



        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("이미지를 업로드해주세요");
        }


        // 서버용
//        Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, userId);
        // 테스트용
//        Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, 7L);
//
//        return ResponseEntity.ok(saved);

        String imageUrl = null;
        try {
            System.out.println("==== 이미지 파일: " + image.getOriginalFilename());

            // DTO 값 확인
            System.out.println("==== 레시피 DTO ====");
            System.out.println("title: " + recipeCreateRequestDto.getTitle());
            System.out.println("subTitle: " + recipeCreateRequestDto.getSubTitle());
            System.out.println("description: " + recipeCreateRequestDto.getDescription());
            System.out.println("serving: " + recipeCreateRequestDto.getServing());
            System.out.println("categoryId: " + recipeCreateRequestDto.getCategoryId());
            System.out.println("ingredients: " + recipeCreateRequestDto.getIngredients());
            System.out.println("seasonings: " + recipeCreateRequestDto.getSeasonings());
            System.out.println("steps: " + recipeCreateRequestDto.getSteps());
            System.out.println("===================");

            // S3 업로드
            imageUrl = s3UploaderService.uploadFile(image);
            System.out.println("==== 이미지 URL: " + imageUrl);

            // 저장
            Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, 7L);
            System.out.println("==== 저장 완료, recipeId: " + saved.getId());

            return ResponseEntity.ok(saved);
        } catch(Exception e) {
            e.printStackTrace(); // 어디서 터졌는지 로그 확인
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getClass().getSimpleName(), "message", e.getMessage()));
        }
    }
}
