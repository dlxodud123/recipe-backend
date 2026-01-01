package com.taeyoung.recipe.recipe_backend.dto.member.request;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateRequestDto {

    @NotBlank
    private String name;

    private String zipcode;
    private String address;
    private String detailAddress;
    private Gender gender;

    public UpdateRequestDto(String name, String zipcode, String address, String detailAddress, Gender gender) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.gender = gender;
    }
}
