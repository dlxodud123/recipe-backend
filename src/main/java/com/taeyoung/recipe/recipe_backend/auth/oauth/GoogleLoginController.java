package com.taeyoung.recipe.recipe_backend.auth.oauth;

import com.taeyoung.recipe.recipe_backend.auth.jwt.JwtUtil;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
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
    @GetMapping("/oauth/google/login")
    public String login() {
        return "redirect:" + googleOAuthService.getLoginUrl();
    }

    // 구글 인증 후 콜백
    @GetMapping("/oauth/google/callback")
    public String callback(@RequestParam String code, Model model, HttpServletResponse response) throws IOException {
        String accessToken = googleOAuthService.getAccessToken(code);
        Map<String, Object> userInfo = googleOAuthService.getUserInfo(accessToken);

        String email = (String) userInfo.get("email");
        ProviderType provider = ProviderType.GOOGLE;
        String providerId = (String) userInfo.get("id");

        System.out.println("userInfo : " + userInfo);
        System.out.println("providerId" + providerId);

        // 연동이 완료된 member가 db에 있는지 확인
        Member member = googleLoginService.findByProviderAndProviderId(provider, providerId);

        if (member != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    member,
                    null,
                    List.of(new SimpleGrantedAuthority(member.getRole().name()))
            );

            String jwt = JwtUtil.createToken(authentication);

            // 쿠키에 JWT 저장
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(false);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(1000);
            response.addCookie(cookie);

            // JWT를 응답으로도 전달 (선택)
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"jwt\":\"" + jwt + "\"}");

//                model.addAttribute("alreadyJoined", true);
            return null;
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