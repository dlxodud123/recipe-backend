package com.taeyoung.recipe.recipe_backend.dto.member.request.find;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FindPasswordRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String username;

    public FindPasswordRequestDto(String name, String username) {
        this.name = name;
        this.username = username;
    }
}
