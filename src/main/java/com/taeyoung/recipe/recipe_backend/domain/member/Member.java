package com.taeyoung.recipe.recipe_backend.domain.member;

import com.taeyoung.recipe.recipe_backend.domain.study.Comment;
import com.taeyoung.recipe.recipe_backend.domain.study.Study;
import com.taeyoung.recipe.recipe_backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;
    private String phone;
    private Boolean ageConsent;
    private String address;
    private String zipcode;
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

    public Member(ProviderType provider, String username, String password, Role role, String name, String phone, Boolean ageConsent, String address, String zipcode, LocalDate birthDate, Gender gender) {
        this.provider = provider;
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.ageConsent = ageConsent;
        this.address = address;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void updateMember(String password, String email) {
        this.password = password;
//        this.email = email;
    }

    public void setRandomPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }
}

