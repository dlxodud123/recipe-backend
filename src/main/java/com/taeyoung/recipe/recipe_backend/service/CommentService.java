package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.recipe.*;
import com.taeyoung.recipe.recipe_backend.dto.comment.request.CommentCreateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.comment.response.CommentByIdResponseDto;
import com.taeyoung.recipe.recipe_backend.repository.comment.CommentRepository;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;

    public Comment save(CommentCreateRequestDto dto, Long recipeId, long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("레시피가 존재하지 않습니다."));

        Comment comment = new Comment(dto.getComment(), member, recipe);
        member.getComments().add(comment);
        recipe.getComments().add(comment);

        return commentRepository.save(comment);
    }
    @Transactional(readOnly = true)
    public List<CommentByIdResponseDto> getCommentById(Long recipeId) {
        List<Comment> comments = commentRepository.findAllByRecipeIdOrderByCreatedAtDesc(recipeId);
        return comments.stream()
            .map(CommentByIdResponseDto::from)
            .toList();
    }
}
