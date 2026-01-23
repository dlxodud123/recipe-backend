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
        Long userId = ((CustomUser) authentication.getPrincipal()).getId();

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("이미지를 업로드해주세요");
        }

        String imageUrl = s3UploaderService.uploadFile(image);

        // 서버용
//        Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, userId);
        // 테스트용
//        Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, 7L);
//
//        return ResponseEntity.ok(saved);
        try {
            Recipe saved = recipeService.save(recipeCreateRequestDto, imageUrl, 7L);
            return ResponseEntity.ok(saved);
        } catch(Exception e) {
            e.printStackTrace(); // 어디서 터졌는지 로그 확인
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
