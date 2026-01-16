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
        String providerId = (String) userInfo.get("sub");

        System.out.println("userInfo : " + userInfo);
        System.out.println("providerId : " + providerId);

        // 🔹 가입 여부 판단
//        boolean exists = googleLoginService.existsByProviderAndProviderId(provider, providerId);
        Member member = googleLoginService.findByProviderAndProviderId(provider, providerId);

        if (member != null) {
            // ✅ 이미 가입 → JWT 발급 및 쿠키 저장
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    member,
                    null,
                    List.of(new SimpleGrantedAuthority(member.getRole().name()))
            );

            String jwt = JwtUtil.createToken(authentication);

            // 쿠키 설정
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);  // 배포 환경에서는 반드시 true
            cookie.setSecure(true);    // HTTPS 환경 필수
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24); // 1일
            response.addCookie(cookie);

            // JWT를 JSON 형태로 클라이언트에도 전달 (선택)
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"jwt\":\"" + jwt + "\"}");
            response.getWriter().flush();

            // 더 이상 view 리턴 X, 응답 처리 완료
            return null;
        } else {
            // ✅ 신규 가입 → 연동 페이지로 이동
            String redirectUrl = "/member/socialSignup"
                    + "?provider=" + provider
                    + "&providerId=" + providerId
                    + "&username=" + email;
            return "redirect:" + redirectUrl;
        }
    }
}