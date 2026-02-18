package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.UpdateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindPasswordRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindUsernameRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.response.MyPageResponseDto;
import com.taeyoung.recipe.recipe_backend.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입(아이디 중복 확인) !!
    @GetMapping("/signup/username/{username}")
    public ResponseEntity<String> checkUsernameDuplicate(@PathVariable String username){
        memberService.isUsernameDuplicated(username);
        return ResponseEntity.ok("사용가능한 아이디입니다");
    }

    // 회원가입(이메일 중복 확인) !!
    @GetMapping("/signup/email/{email}")
    public ResponseEntity<String> checkEmailDuplicate(@PathVariable String email){
        memberService.isEmailDuplicated(email);
        return ResponseEntity.ok("사용가능한 이메일입니다");
    }

    // 회원가입 !!
    @PostMapping("/signup")
    public ResponseEntity<String> registerMember(@Valid @RequestBody SignupRequestDto signupRequestDto){
        memberService.registerMember(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공!");
    }

    // 회원 정보 조회 !!
    @GetMapping("/me")
    public ResponseEntity<MyPageResponseDto> getMyInfo(Authentication authentication){

        MyPageResponseDto myPageDto = memberService.getMyInfo(((CustomUser) authentication.getPrincipal()).getId());
        return ResponseEntity.ok(myPageDto);
    }

    // 회원 정보 수정 !!
    @PutMapping("/me")
    public ResponseEntity<String> updateMyInfo(@Valid @RequestBody UpdateRequestDto updateRequestDto, Authentication authentication){

        memberService.updateMember(updateRequestDto, ((CustomUser) authentication.getPrincipal()).getId());

        return ResponseEntity.ok("회원수정 성공!");
    }

    // 회원 탈퇴 !!
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyAccount(Authentication authentication, HttpServletResponse response){

        memberService.deleteMember(((CustomUser) authentication.getPrincipal()).getId());

        // JWT 쿠키 삭제
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
//        cookie.setHttpOnly(false);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok().build();
    }

    // 회원 연동 !!
    @PostMapping("/me/social")
    public ResponseEntity<String> linkMyInfo(Authentication authentication){

        memberService.linkMember(((CustomUser) authentication.getPrincipal()).getId());

        return ResponseEntity.ok("회원연동 성공!"); 
    }

    // 회원 연동 해제 !!
    @DeleteMapping("/me/social")
    public ResponseEntity<String> deleteLinkMyInfo(Authentication authentication){

        memberService.deleteLinkMember(((CustomUser) authentication.getPrincipal()).getId());

        return ResponseEntity.ok("회원연동 해제 성공!");
    }

    // username 찾기 !!
    @PostMapping("/find-username")
    public ResponseEntity<String> findUsername(@Valid @RequestBody FindUsernameRequestDto findUsernameRequestDto) {
        String findUsername = memberService.findUsername(findUsernameRequestDto);

        return ResponseEntity.ok(findUsername);
    }

    // password 찾기 !!
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@Valid @RequestBody FindPasswordRequestDto findPasswordRequestDto) {
        String findPassword = memberService.findPassword(findPasswordRequestDto);

        return ResponseEntity.ok(findPassword);
    }
}
