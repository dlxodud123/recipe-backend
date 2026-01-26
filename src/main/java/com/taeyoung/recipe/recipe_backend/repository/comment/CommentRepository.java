package com.taeyoung.recipe.recipe_backend.repository.comment;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 최신 댓글이 맨 위로 오도록
    List<Comment> findAllByRecipeIdOrderByCreatedAtDesc(Long recipeId);


}
