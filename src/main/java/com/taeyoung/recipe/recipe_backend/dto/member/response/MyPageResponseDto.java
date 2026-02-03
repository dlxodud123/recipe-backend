package com.taeyoung.recipe.recipe_backend.dto.member.response;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class MyPageResponseDto {

    private String username;
    private String email;
    private String name;
    private String zipcode;
    private String address;
    private String detailAddress;
    private LocalDate birthDate;
    private Gender gender;

    private boolean linked; // 연동 여부 추가
    private String provider; // LOCAL / GOOGLE

    // 정적 팩토리 메서드
    public static MyPageResponseDto from(Member member, boolean isLinked) {
        return new MyPageResponseDto(
                member.getUsername(),
                member.getEmail(),
                member.getName(),
                member.getZipcode(),
                member.getAddress(),
                member.getDetailAddress(),
                member.getBirthDate(),
                member.getGender(),
                isLinked,
                member.getProvider().name()
        );
    }
}
