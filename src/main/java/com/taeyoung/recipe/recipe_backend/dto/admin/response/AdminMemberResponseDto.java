package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AdminMemberResponseDto {
    private Long id;
    private String name;
    private String joined;
    private String provider;
    private String role;

    public AdminMemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName() != null ? member.getName() : "";
        this.joined = member.getCreatedAt() != null
                ? member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
        this.provider = member.getProvider() != null ? member.getProvider().name() : "";
        this.role = member.getRole() != null ? member.getRole().name() : "";
    }
}
