package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.*;
import com.taeyoung.recipe.recipe_backend.repository.comment.CommentRepository;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.repository.recipe.RecipeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // 대시보드
    @Transactional(readOnly = true)
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







    // 전체 회원 조회
    @Transactional(readOnly = true)
    public Page<AdminMemberResponseDto> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(AdminMemberResponseDto::new);
    }
    // 회원 상세 조회
    @Transactional(readOnly = true)
    public AdminMemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        return new AdminMemberDetailResponseDto(member);
    }
    // 회원 삭제
    public void deleteMember(Long id){
        // 삭제할 회원 가져오기
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        // 연동된 멤버 링크 끊기
        Member linkedMember = memberRepository.findByLinkedMemberId(id).orElse(null);

        if (linkedMember != null) {
            linkedMember.unlink();  // linkedMemberId = null
            memberRepository.save(linkedMember); // 변경을 DB에 반영
        }

        memberRepository.delete(member);
    }
    // 회원 권한 변경
    public void changeMemberRole(Long id, String newRole) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        Role newRoleEnum = Role.valueOf(newRole);
        member.setRole(newRoleEnum);

        memberRepository.save(member);
    }








    // 전체 레시피 조회
    @Transactional(readOnly = true)
    public Page<AdminRecipeResponseDto> getRecipes(Pageable pageable) {
        return recipeRepository.findAdminRecipes(pageable);
    }
    // 레시피 상세 조회
    @Transactional(readOnly = true)
    public AdminRecipeDetailResponseDto getRecipeDetail(Long recipeId) {
        Recipe recipe = recipeRepository.findRecipeWithIngredientsAndSteps(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("레시피가 존재하지 않습니다."));

        return new AdminRecipeDetailResponseDto(recipe);
    }
    // 레시피 삭제
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("레시피가 존재하지 않습니다."));

        recipeRepository.delete(recipe);
    }










    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public Page<AdminCommentResponseDto> getComments(Pageable pageable) {
        return commentRepository.findAll(pageable)
                .map(AdminCommentResponseDto::new);
    }
    // 댓글 상세 조회
    @Transactional(readOnly = true)
    public AdminCommentResponseDto getCommentDetail(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        return new AdminCommentResponseDto(comment);
    }
    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("댓글이 존재하지 않습니다."));

        commentRepository.delete(comment);
    }






}
