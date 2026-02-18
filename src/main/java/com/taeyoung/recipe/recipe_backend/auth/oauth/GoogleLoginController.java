package com.taeyoung.recipe.recipe_backend.auth.oauth;

import com.taeyoung.recipe.recipe_backend.auth.jwt.JwtUtil;
import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GoogleLoginController {

    private final GoogleOAuthService googleOAuthService;
    private final GoogleLoginService googleLoginService;

    // 구글 로그인 버튼 클릭 → 로그인 URL로 리다이렉트
    @GetMapping("/api/oauth/google/login")
    public String login() {
        return "redirect:" + googleOAuthService.getLoginUrl();
    }

    // 구글 인증 후 콜백
    @GetMapping("/api/oauth/google/callback")
    public String callback(@RequestParam String code, Model model, HttpServletResponse response) throws IOException {
        String accessToken = googleOAuthService.getAccessToken(code);
        Map<String, Object> userInfo = googleOAuthService.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        ProviderType provider = ProviderType.GOOGLE;
        String providerId = (String) userInfo.get("id");

        Member member = googleLoginService.findByProviderAndProviderId(provider, providerId);

        if (member == null) {
            member = Member.createSocialMember(email, provider, providerId, Role.USER);
            googleLoginService.registerSocialMember(member);
        }

        sendJwtToResponse(member, response);

        return "redirect:https://mealhub.site";
    }

    // JWT 생성 + 쿠키 설정 + JSON 응답
    private void sendJwtToResponse(Member member, HttpServletResponse response) throws IOException {
        CustomUser customUser = new CustomUser(
                member.getId(),
                member.getEmail(),
                "",
                member.getProvider(),
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
        );

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            customUser,
            null,
            customUser.getAuthorities()
        );

        String jwt = JwtUtil.createToken(authentication);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(1000);
        response.addCookie(cookie);
    }
}