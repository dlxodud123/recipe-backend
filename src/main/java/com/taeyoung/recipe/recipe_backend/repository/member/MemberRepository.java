package com.taeyoung.recipe.recipe_backend.repository.member;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 회원가입 (중복확인)
    boolean existsByUsername(String username);
    boolean existsByEmailAndProvider(String email, ProviderType providerType);

    // myPage 조회
    boolean existsByLinkedMemberId(Long linkedMemberId);

    // 이메일로 회원 조회
    Optional<Member> findByEmail(String email);

    // 연동
    Optional<Member> findByEmailAndProvider(String email, ProviderType provider);
    Optional<Member> findByEmailAndProviderNot(String email, ProviderType provider);

    // 연동 해제
    Optional<Member> findByLinkedMemberId(Long linkedMemberId);

    // jwt
    Optional<Member> findByUsername(String username);

    // find
    Optional<Member> findByNameAndEmail(String name, String email);
    Optional<Member> findByNameAndUsername(String name, String username);

    // google login
    Member findByProviderAndProviderId(ProviderType provider, String providerId);
}
