package com.taeyoung.recipe.recipe_backend.domain.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -125331984L;

    public static final QMember member = new QMember("member1");

    public final com.taeyoung.recipe.recipe_backend.global.entity.QBaseEntity _super = new com.taeyoung.recipe.recipe_backend.global.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final BooleanPath ageConsent = createBoolean("ageConsent");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final ListPath<com.taeyoung.recipe.recipe_backend.domain.comment.Comment, com.taeyoung.recipe.recipe_backend.domain.comment.QComment> comments = this.<com.taeyoung.recipe.recipe_backend.domain.comment.Comment, com.taeyoung.recipe.recipe_backend.domain.comment.QComment>createList("comments", com.taeyoung.recipe.recipe_backend.domain.comment.Comment.class, com.taeyoung.recipe.recipe_backend.domain.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath detailAddress = createString("detailAddress");

    public final StringPath email = createString("email");

    public final EnumPath<Gender> gender = createEnum("gender", Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> linkedMemberId = createNumber("linkedMemberId", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<ProviderType> provider = createEnum("provider", ProviderType.class);

    public final StringPath providerId = createString("providerId");

    public final ListPath<com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe, com.taeyoung.recipe.recipe_backend.domain.recipe.QRecipe> recipes = this.<com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe, com.taeyoung.recipe.recipe_backend.domain.recipe.QRecipe>createList("recipes", com.taeyoung.recipe.recipe_backend.domain.recipe.Recipe.class, com.taeyoung.recipe.recipe_backend.domain.recipe.QRecipe.class, PathInits.DIRECT2);

    public final EnumPath<Role> role = createEnum("role", Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public final StringPath zipcode = createString("zipcode");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

