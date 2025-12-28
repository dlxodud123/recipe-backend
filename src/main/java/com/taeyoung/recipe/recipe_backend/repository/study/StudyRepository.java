package com.taeyoung.recipe.recipe_backend.repository.study;

import com.taeyoung.recipe.recipe_backend.domain.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long>, StudyRepositoryCustom {
}
