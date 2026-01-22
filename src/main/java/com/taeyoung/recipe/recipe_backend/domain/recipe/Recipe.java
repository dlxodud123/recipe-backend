package com.taeyoung.recipe.recipe_backend.domain.recipe;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Recipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String subTitle;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String serving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ingredientOrder ASC")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("seasoningOrder ASC")
    private List<RecipeSeasoning> seasonings = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stepOrder ASC")
    private List<RecipeStep> steps = new ArrayList<>();


    public Recipe() {
    }


    // 작성자 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.addRecipe(this);
    }

    // 기본 정보 편의 메서드
    public Recipe setBasicInfo(String title, String subTitle, String description, String serving,
                             Category category, String imageUrl) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.serving = serving;
        this.category = category;
        this.imageUrl = imageUrl;
        return this;
    }

    // 재료/양념/단계 편의 메서드
    public void addIngredient(RecipeIngredient ingredient) {
        ingredient.assignToRecipe(this);
        this.ingredients.add(ingredient);
    }
    public void addSeasoning(RecipeSeasoning seasoning) {
        seasoning.assignToRecipe(this);
        this.seasonings.add(seasoning);
    }
    public void addStep(RecipeStep step) {
        step.assignToRecipe(this);
        this.steps.add(step);
    }
}
