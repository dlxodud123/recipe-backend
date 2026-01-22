package com.taeyoung.recipe.recipe_backend.domain.recipe;

import jakarta.persistence.*;

@Entity
@Table(
    name = "recipe_ingredient",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "ingredient_order"})
    }
)
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private String name;

    // 순서
    @Column(name = "ingredient_order", nullable = false)
    private int ingredientOrder;
}
