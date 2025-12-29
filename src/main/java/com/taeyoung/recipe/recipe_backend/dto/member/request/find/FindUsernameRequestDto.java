package com.taeyoung.recipe.recipe_backend.dto.member.request.find;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FindUsernameRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    public FindUsernameRequestDto(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
