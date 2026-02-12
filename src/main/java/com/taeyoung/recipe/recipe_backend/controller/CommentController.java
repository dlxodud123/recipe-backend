package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.dto.comment.request.CommentCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.comment.response.CommentByIdResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.recipe.response.RecipeByIdResponseDto;
import com.taeyoung.recipe.recipe_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{recipeId}")
    public Comment createComment(@PathVariable Long recipeId,
                                    @RequestBody CommentCreateRequestDto commentCreateRequestDto,
                                    Authentication authentication){
        Long userId = ((CustomUser) authentication.getPrincipal()).getId();

        return commentService.save(commentCreateRequestDto, recipeId, userId);
    }
    @GetMapping("/{recipeId}")
    public List<CommentByIdResponseDto> getCommentById(@PathVariable Long recipeId) {
        return commentService.getCommentById(recipeId);
    }
}
