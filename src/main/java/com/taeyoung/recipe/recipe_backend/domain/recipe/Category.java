package com.taeyoung.recipe.recipe_backend.domain.recipe;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "category_order", nullable = false, unique = true)
    private int categoryOrder;

    @OneToMany(mappedBy = "category")
    private List<Recipe> recipes = new ArrayList<>();


    public Category() {
    }
}
