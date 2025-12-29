package com.taeyoung.recipe.recipe_backend.dto.member.request;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String phone;

    private Boolean ageConsent;
    private String address;
    private String zipcode;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public SignupRequestDto(String username, String password, String name, String phone, Boolean ageConsent, String address, String zipcode, LocalDate birthDate, Gender gender) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.ageConsent = ageConsent;
        this.address = address;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
