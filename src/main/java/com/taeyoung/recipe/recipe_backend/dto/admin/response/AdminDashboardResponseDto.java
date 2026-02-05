package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AdminDashboardResponseDto {

    private final long memberCount;
    private final long recipeCount;
    private final long commentCount;

    private final List<AdminDashboardMemberResponseDto> recentMembers;
    private final List<AdminDashboardRecipeResponseDto> recentRecipes;
    private final List<AdminDashboardCommentResponseDto> recentComments;

    public static AdminDashboardResponseDto from(
            long memberCount,
            long recipeCount,
            long commentCount,
            List<Member> members,
            List<Recipe> recipes,
            List<Comment> comments
    ) {
        return new AdminDashboardResponseDto(
                memberCount,
                recipeCount,
                commentCount,
                members.stream().map(AdminDashboardMemberResponseDto::from).toList(),
                recipes.stream().map(AdminDashboardRecipeResponseDto::from).toList(),
                comments.stream().map(AdminDashboardCommentResponseDto::from).toList()
        );
    }
}