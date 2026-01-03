package com.taeyoung.recipe.recipe_backend.auth.oauth;

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

    // username 중복 체크
    public boolean checkUsernameExists(String username) {
        return memberRepository.existsByUsername(username);
    }

    public boolean existsByProviderAndProviderId(ProviderType provider, String providerId) {
        return memberRepository.existsByProviderAndProviderId(provider, providerId);
    }
}
