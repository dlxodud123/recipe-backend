package com.taeyoung.recipe.recipe_backend.auth.oauth;

import com.taeyoung.recipe.recipe_backend.auth.jwt.JwtUtil;
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

        if (member == null) {
            member = Member.createSocialMember(email, provider, providerId, Role.USER);
            googleLoginService.registerSocialMember(member);
        }

        // JWT 발급 및 쿠키/응답 처리
        sendJwtToResponse(member, response);

        // 뷰 반환 없이 JSON 응답만
        return null;
    }

    // JWT 생성 + 쿠키 설정 + JSON 응답
    private void sendJwtToResponse(Member member, HttpServletResponse response) throws IOException {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member,
                null,
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
        );

        String jwt = JwtUtil.createToken(authentication);

        Cookie cookie = new Cookie("jwt", jwt);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // HTTPS 필수
        cookie.setHttpOnly(false); // 로컬 테스트용
        cookie.setSecure(false); // 로컬 테스트용
        cookie.setPath("/");
        cookie.setMaxAge(1000);
        response.addCookie(cookie);

        // SameSite=None 직접 헤더에 추가
//        String header = String.format("jwt=%s; Max-Age=%d; Path=/; SameSite=None%s",
//                jwt, 1000, cookie.getSecure() ? "; Secure" : "");
//        response.addHeader("Set-Cookie", header);

        // 로컬호스트 콘솔 확인용
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"jwt\":\"" + jwt + "\"}");
    }
}