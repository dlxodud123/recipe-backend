package com.taeyoung.recipe.recipe_backend.dto.member.response;

import lombok.Getter;

@Getter
public class MemberResponseDto {

    private String username;
    private String email;

    public MemberResponseDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
