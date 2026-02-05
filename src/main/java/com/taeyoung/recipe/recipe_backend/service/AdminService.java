package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminDashboardResponseDto;
import com.taeyoung.recipe.recipe_backend.repository.comment.CommentRepository;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final CommentRepository commentRepository;

    public AdminDashboardResponseDto getDashboard() {

        long memberCount = memberRepository.count();
        long recipeCount = recipeRepository.count();
        long commentCount = commentRepository.count();

        List<Member> recentMembers =
                memberRepository.findTop5ByOrderByCreatedAtDesc();

        List<Recipe> recentRecipes =
                recipeRepository.findTop5ByOrderByCreatedAtDesc();

        List<Comment> recentComments =
                commentRepository.findTop5ByOrderByCreatedAtDesc();

        return AdminDashboardResponseDto.from(
                memberCount,
                recipeCount,
                commentCount,
                recentMembers,
                recentRecipes,
                recentComments
        );
    }
}
