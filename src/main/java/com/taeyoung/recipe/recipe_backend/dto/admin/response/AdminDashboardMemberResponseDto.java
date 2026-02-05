package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminDashboardMemberResponseDto {

    private final Long memberId;
    private final String name;
    private final String createdAt;
    private final String provider;
    private final boolean linked;

    public static AdminDashboardMemberResponseDto from(Member member) {
        return new AdminDashboardMemberResponseDto(
                member.getId(),
                member.getName(),
                member.getCreatedAt().toLocalDate().toString(),
                member.getProvider().name(),   // LOCAL / GOOGLE
                member.getLinkedMemberId() != null
        );
    }
}

