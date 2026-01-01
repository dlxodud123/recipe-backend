package com.taeyoung.recipe.recipe_backend.controller;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.UpdateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindPasswordRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindUsernameRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.response.MyPageResponseDto;
import com.taeyoung.recipe.recipe_backend.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입(아이디 중복 확인) !!
    @GetMapping("/signup/{username}")
    public ResponseEntity<String> checkUsernameDuplicate(@PathVariable String username){
        memberService.isUsernameDuplicated(username);
        return ResponseEntity.ok("사용가능한 아이디입니다");
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
        CustomUser user = (CustomUser) authentication.getPrincipal();
        // 서버 배포용
//        return ResponseEntity.ok(memberService.getMyInfo(authentication));

        // 로컬 테스트용
        return ResponseEntity.ok(memberService.finById(1L));
    }

    // 회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMyAccount(Authentication authentication){
        CustomUser user = (CustomUser) authentication.getPrincipal();

        memberService.deleteMember(user.getId());
        return ResponseEntity.ok("회원탈퇴 성공!");
    }

    // 회원 정보 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateMyInfo(@Valid @RequestBody UpdateRequestDto updateRequestDto, Authentication authentication){
        CustomUser user = (CustomUser) authentication.getPrincipal();

        memberService.updateMember(updateRequestDto, user.getId());
        return ResponseEntity.ok("회원수정 성공!");
    }

    // username 찾기 !!
    @PostMapping("/find-username")
    public ResponseEntity<String> findUsername(@Valid @RequestBody FindUsernameRequestDto findUsernameRequestDto) {
        String findUsername = memberService.findUsername(findUsernameRequestDto);

        return ResponseEntity.ok(findUsername);
    }

    // password 찾기 !!
    @PostMapping("/find-password")
    public ResponseEntity<String> findPassword(@RequestBody FindPasswordRequestDto findPasswordRequestDto) {
        String findPassword = memberService.findPassword(findPasswordRequestDto);

        return ResponseEntity.ok(findPassword);
    }
}
