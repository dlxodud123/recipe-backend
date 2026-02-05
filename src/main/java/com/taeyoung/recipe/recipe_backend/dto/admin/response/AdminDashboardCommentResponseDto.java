package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminDashboardCommentResponseDto {

    private final Long commentId;
    private final String content;
    private final String createdAt;
    private final String authorName;

    public static AdminDashboardCommentResponseDto from(Comment comment) {
        return new AdminDashboardCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedAt().toLocalDate().toString(),
                comment.getMember().getName()
        );
    }
}

