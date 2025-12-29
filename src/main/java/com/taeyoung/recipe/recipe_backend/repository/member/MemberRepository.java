package com.taeyoung.recipe.recipe_backend.repository.member;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);

    // jwt
    Optional<Member> findByUsername(String username);

    // find
    Optional<Member> findByNameAndPhone(String name, String phone);
}
