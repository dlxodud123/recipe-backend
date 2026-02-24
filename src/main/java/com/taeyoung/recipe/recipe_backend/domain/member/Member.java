package com.taeyoung.recipe.recipe_backend.domain.member;

import com.taeyoung.recipe.recipe_backend.domain.comment.Comment;
import com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe;
import com.taeyoung.recipe.recipe_backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // 연동 대상 (LOCAL 계정의 member_id)
    @Column(name = "linked_member_id")
    private Long linkedMemberId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String name;
    private String phone;
    private Boolean ageConsent;
    private String zipcode;
    private String address;
    private String detailAddress;
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Recipe> recipes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Member() {
    }

    public Member(ProviderType provider, Role role, String username, String password, String email, String name, String phone, Boolean ageConsent, String zipcode, String address, String detailAddress, LocalDate birthDate, Gender gender) {
        this.provider = provider;
        this.role = role;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public static Member createSocialMember(String email, ProviderType provider, String providerId, Role role) {
        Member member = new Member();
        member.email = email;
        member.provider = provider;
        member.providerId = providerId;
        member.role = role;
        return member;
    }

    public void link(Long localMemberId) {
        this.linkedMemberId = localMemberId;
    }
    public void unlink() {
        this.linkedMemberId = null;
    }


    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe); // Member쪽 리스트에 추가
    }
    public void removeRecipe(Recipe recipe) {
        this.recipes.remove(recipe); // Member쪽 리스트에서 제거
        recipe.setMember(null);      // Recipe쪽 member를 null로
    }

    // test용 setter 함수
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}

