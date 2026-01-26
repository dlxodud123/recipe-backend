package com.taeyoung.recipe.recipe_backend.dto.comment.response;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentByIdResponseDto {
    private Long id;
    private String content;
    private String memberName;
    private Long memberId;
    private LocalDateTime createdAt;

    public static CommentByIdResponseDto from(Comment comment) {
        return new CommentByIdResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getName(),
                comment.getMember().getId(),
                comment.getCreatedAt()
        );
    }
}
