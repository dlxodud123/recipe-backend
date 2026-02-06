package com.taeyoung.recipe.recipe_backend.dto.admin.response;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class AdminMemberDetailResponseDto {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String phone;
    private String birthDate;
    private String zipcode;
    private String address;
    private String detailAddress;
    private String gender;
    private String joined;
    private String provider;
    private String linked;
    private String role;

    public AdminMemberDetailResponseDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername() != null ? member.getUsername() : "";
        this.email = member.getEmail() != null ? member.getEmail() : "";
        this.name = member.getName() != null ? member.getName() : "";
        this.phone = member.getPhone() != null ? member.getPhone() : "";
        this.birthDate = member.getBirthDate() != null ? member.getBirthDate().toString() : "";
        this.zipcode = member.getZipcode() != null ? member.getZipcode() : "";
        this.address = member.getAddress() != null ? member.getAddress() : "";
        this.detailAddress = member.getDetailAddress() != null ? member.getDetailAddress() : "";
        this.gender = member.getGender() != null
                ? (member.getGender() == Gender.F ? "여성" : "남성")
                : "";
        this.joined = member.getCreatedAt() != null
                ? member.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                : "";
        this.provider = member.getProvider() != null ? member.getProvider().name() : "";
        this.linked = member.getLinkedMemberId() != null ? "O" : "X";
        this.role = member.getRole() != null ? member.getRole().name() : "";
    }
}
