package com.taeyoung.recipe.recipe_backend.domain.recipe;

import jakarta.persistence.*;

@Entity
@Table(
    name = "recipe_seasoning",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "seasoning_order"})
    }
)
public class RecipeSeasoning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private String name;

    // 순서
    @Column(name = "seasoning_order", nullable = false)
    private int seasoningOrder;


    public void setNameAndOrder(String name, int order) {
        this.name = name;
        this.seasoningOrder = order;
    }

    public void assignToRecipe(Recipe recipe){
        this.recipe = recipe;
    }
}
