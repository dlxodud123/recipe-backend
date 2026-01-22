package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.recipe.request.RecipeCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.service.RecipeService;
import com.taeyoung.recipe.recipe_backend.service.S3UploaderService;
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
                                              @RequestPart("recipe") RecipeCreateRequestDto recipeCreateRequestDto,
                                              Authentication authentication) throws IOException {
        CustomUser user = (CustomUser) authentication.getPrincipal();

        String imageUrl = s3UploaderService.uploadFile(image);
        recipeCreateRequestDto.setImageUrl(imageUrl);
        Recipe saved = recipeService.save(recipeCreateRequestDto);

        return ResponseEntity.ok(saved);
    }
}
