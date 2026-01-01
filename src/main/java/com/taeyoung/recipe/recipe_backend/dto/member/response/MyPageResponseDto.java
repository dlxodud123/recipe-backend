package com.taeyoung.recipe.recipe_backend.dto.member.response;

import lombok.Getter;

@Getter
public class MyPageResponseDto {

    private String username;
    private String name;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String birthDate;
    private String gender;

    public MyPageResponseDto(String username, String name, String zipcode, String address, String detailAddress, String birthDate, String gender) {
        this.username = username;
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
