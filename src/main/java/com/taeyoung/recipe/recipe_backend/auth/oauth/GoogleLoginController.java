package com.taeyoung.recipe.recipe_backend.auth.oauth;

import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GoogleLoginController {
    
    private final GoogleOAuthService googleOAuthService;
    private final GoogleLoginService googleLoginService;

    // 구글 로그인 버튼 클릭 → 로그인 URL로 리다이렉트
    @GetMapping("/oauth/google/login")
    public String login() {
        return "redirect:" + googleOAuthService.getLoginUrl();
    }

    // 구글 인증 후 콜백
    @GetMapping("/oauth/google/callback")
    public String callback(@RequestParam String code, Model model) {
        String accessToken = googleOAuthService.getAccessToken(code);
        Map<String, Object> userInfo = googleOAuthService.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        ProviderType provider = ProviderType.GOOGLE;
        String providerId = (String) userInfo.get("sub");

        // 🔹 가입 여부 판단
        boolean exists = googleLoginService.existsByProviderAndProviderId(provider, providerId);

        if (exists) {
            // 이미 가입 → 로그인 처리 or 안내
            model.addAttribute("alreadyJoined", true);
        } else {
            // 신규 가입
            model.addAttribute("provider", provider);
            model.addAttribute("providerId", providerId);

            // username은 email로 자동 세팅 + readonly
            model.addAttribute("username", email);
            model.addAttribute("readonlyUsername", true);
        }

        return "member/socialSignup";
    }
}