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
    public ResponseEntity<String> createStudy(@RequestPart("image") MultipartFile image,
                                         @Valid @RequestPart("recipe") RecipeCreateRequestDto recipeCreateRequestDto,
                                         Authentication authentication) throws IOException {
//        Long userId = ((CustomUser) authentication.getPrincipal()).getId();

        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("이미지를 업로드해주세요");
        }

        String imageUrl = s3UploaderService.uploadFile(image);

        // 서버용
//        recipeService.save(recipeCreateRequestDto, imageUrl, userId);
//        return ResponseEntity.ok(saved);


        recipeService.save(recipeCreateRequestDto, imageUrl, 7L);

        return ResponseEntity.ok("레시피 저장 완료!");
    }
}
