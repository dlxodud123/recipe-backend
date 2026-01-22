package com.taeyoung.recipe.recipe_backend.domain.recipe;

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
}
