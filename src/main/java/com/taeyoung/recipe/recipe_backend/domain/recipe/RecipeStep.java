package com.taeyoung.recipe.recipe_backend.domain.recipe;

import jakarta.persistence.*;

@Entity
@Table(
    name = "recipe_step",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "step_order"})
    }
)
public class RecipeStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 순서
    @Column(name = "step_order", nullable = false)
    private int stepOrder;
}
