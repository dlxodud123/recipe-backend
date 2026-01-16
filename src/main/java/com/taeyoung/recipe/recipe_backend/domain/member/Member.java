package com.taeyoung.recipe.recipe_backend.domain.member;

import com.taeyoung.recipe.recipe_backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(
    name = "member",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"provider", "provider_id"}
        )
    }
)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Column(name = "provider_id")
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String username;

//    @Column(unique = true)
//    private String email;

    private String password;
    private String name;
    private String phone;
    private Boolean ageConsent;
    private String zipcode;
    private String address;
    private String detailAddress;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

//    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Recipe> recipes;
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member")
//    private List<Like> likes = new ArrayList<>();

    public Member() {
    }

    public Member(ProviderType provider, Role role, String username, String password, String name, String phone, Boolean ageConsent, String zipcode, String address, String detailAddress, LocalDate birthDate, Gender gender) {
        this.provider = provider;
        this.role = role;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.ageConsent = ageConsent;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void updateMember(String name, String zipcode, String address, String detailAddress, Gender gender) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.gender = gender;
    }

    public void setRandomPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }
}

