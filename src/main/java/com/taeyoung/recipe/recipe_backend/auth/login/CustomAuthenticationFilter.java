package com.taeyoung.recipe.recipe_backend.auth.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taeyoung.recipe.recipe_backend.auth.jwt.JwtUtil;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.dto.member.request.LoginRequestDto;
import com.taeyoung.recipe.recipe_backend.global.exception.EmailNotMatchException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        setFilterProcessesUrl("/api/members/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException("로그인 요청 파싱 실패", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        // JWT 생성
        String jwt = JwtUtil.createToken(authentication);

        // 쿠키에 JWT 저장
        Cookie cookie = new Cookie("jwt", jwt);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // HTTPS 필수
        cookie.setHttpOnly(false);
        cookie.setSecure(false);
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

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String message;
        if (failed instanceof BadCredentialsException) {
            message = "username 또는 password가 일치하지 않습니다.";
        } else if (failed instanceof EmailNotMatchException) {
            message = failed.getMessage();
        } else {
            message = "인증 실패";
        }
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }
}
