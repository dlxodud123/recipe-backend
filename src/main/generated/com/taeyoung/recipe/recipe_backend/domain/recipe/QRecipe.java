package com.taeyoung.recipe.recipe_backend.domain.recipe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipe is a Querydsl query type for Recipe
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecipe extends EntityPathBase<Recipe> {

    private static final long serialVersionUID = -1624302736L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipe recipe = new QRecipe("recipe");

    public final com.taeyoung.recipe.recipe_backend.global.entity.QBaseEntity _super = new com.taeyoung.recipe.recipe_backend.global.entity.QBaseEntity(this);

    public final QCategory category;

    public final ListPath<com.taeyoung.recipe.recipe_backend.domain.comment.Comment, com.taeyoung.recipe.recipe_backend.domain.comment.QComment> comments = this.<com.taeyoung.recipe.recipe_backend.domain.comment.Comment, com.taeyoung.recipe.recipe_backend.domain.comment.QComment>createList("comments", com.taeyoung.recipe.recipe_backend.domain.comment.Comment.class, com.taeyoung.recipe.recipe_backend.domain.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final ListPath<RecipeIngredient, QRecipeIngredient> ingredients = this.<RecipeIngredient, QRecipeIngredient>createList("ingredients", RecipeIngredient.class, QRecipeIngredient.class, PathInits.DIRECT2);

    public final com.taeyoung.recipe.recipe_backend.domain.member.QMember member;

    public final ListPath<RecipeSeasoning, QRecipeSeasoning> seasonings = this.<RecipeSeasoning, QRecipeSeasoning>createList("seasonings", RecipeSeasoning.class, QRecipeSeasoning.class, PathInits.DIRECT2);

    public final NumberPath<Integer> serving = createNumber("serving", Integer.class);

    public final ListPath<RecipeStep, QRecipeStep> steps = this.<RecipeStep, QRecipeStep>createList("steps", RecipeStep.class, QRecipeStep.class, PathInits.DIRECT2);

    public final StringPath subTitle = createString("subTitle");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRecipe(String variable) {
        this(Recipe.class, forVariable(variable), INITS);
    }

    public QRecipe(Path<? extends Recipe> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipe(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipe(PathMetadata metadata, PathInits inits) {
        this(Recipe.class, metadata, inits);
    }

    public QRecipe(Class<? extends Recipe> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.taeyoung.recipe.recipe_backend.domain.member.QMember(forProperty("member")) : null;
    }

}

