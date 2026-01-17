package com.taeyoung.recipe.recipe_backend.auth.oauth;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoogleLoginService {

    private final MemberRepository memberRepository;

    // provider, providerId로 식별
    public Member findByProviderAndProviderId(ProviderType provider, String providerId) {
        return memberRepository.findByProviderAndProviderId(provider, providerId);
    }

    public void registerSocialMember(Member member) {
        memberRepository.save(member);
    }
}
