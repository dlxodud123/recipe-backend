package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AdminCommentResponseDto {
    private Long id;
    private String content;
    private String recipeName;
    private String authorName;
    private String created;

    public AdminCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.recipeName = comment.getRecipe().getTitle();
        this.authorName = comment.getMember().getName();
        this.created = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
