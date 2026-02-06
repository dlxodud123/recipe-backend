package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminDashboardResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberDetailResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberResponseDto;
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
    public Page<AdminMemberResponseDto> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(AdminMemberResponseDto::new);
    }

    // 회원 상세 조회
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

        // 실제 회원 삭제
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
}
