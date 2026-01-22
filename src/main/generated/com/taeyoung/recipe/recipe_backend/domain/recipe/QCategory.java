package com.taeyoung.recipe.recipe_backend.domain.recipe;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = -1026452896L;

    public static final QCategory category = new QCategory("category");

    public final NumberPath<Integer> categoryOrder = createNumber("categoryOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final ListPath<Recipe, QRecipe> recipes = this.<Recipe, QRecipe>createList("recipes", Recipe.class, QRecipe.class, PathInits.DIRECT2);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

