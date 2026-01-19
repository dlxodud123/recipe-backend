package com.taeyoung.recipe.recipe_backend.repository.member;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원가입 (중복확인)
    boolean existsByUsername(String username);
    boolean existsByEmailAndProvider(String email, ProviderType providerType);

    // jwt
    Optional<Member> findByUsername(String username);

    // find
    Optional<Member> findByNameAndEmail(String name, String email);
    Optional<Member> findByNameAndUsername(String name, String username);

    // google login
    Member findByProviderAndProviderId(ProviderType provider, String providerId);

}
