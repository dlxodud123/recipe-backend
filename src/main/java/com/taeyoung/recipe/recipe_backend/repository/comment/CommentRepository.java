package com.taeyoung.recipe.recipe_backend.repository.comment;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
