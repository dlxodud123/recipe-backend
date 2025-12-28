package com.taeyoung.recipe.recipe_backend.repository.study;

import com.taeyoung.recipe.recipe_backend.domain.study.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
