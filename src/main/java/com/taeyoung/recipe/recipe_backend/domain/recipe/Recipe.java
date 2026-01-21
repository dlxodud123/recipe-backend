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

    private String title;
    private String subTitle;
    private String description;
    private String serving;
    private String category;

    private String imageUrl;

    @ElementCollection
    private List<String> ingredients = new ArrayList<>();
    @ElementCollection
    private List<String> seasonings = new ArrayList<>();
    @ElementCollection
    private List<String> steps = new ArrayList<>();

    public Recipe() {
    }
}
