package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.UpdateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.response.MemberResponseDto;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MemberServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MemberService memberService;
    @Autowired private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void before() {
        SignupRequestDto signupRequestDto1 = new SignupRequestDto("user1", "password1", "name1", "01012345678", true, "asdf", "zxcv", "qwer", LocalDate.parse("2001-12-13"), Gender.M);
        SignupRequestDto signupRequestDto2 = new SignupRequestDto("user2", "password1", "name1", "01012345678", true, "asdf", "zxcv", "qwer", LocalDate.parse("2001-12-13"), Gender.M);

        memberService.registerMember(signupRequestDto1);
        memberService.registerMember(signupRequestDto2);
    }

    @Test
    public void signupId() {
        // when, then
//        assertThat(memberService.isUsernameDuplicated("user3")).isEqualTo("ok");
        assertThatThrownBy(() ->
                memberService.isUsernameDuplicated("user1")
        )
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @Test
    public void signup() {
        // when, then
        assertThat(memberService.findByUsername("user1")).isPresent();
        assertThat(memberService.findByUsername("user3")).isNotPresent();
    }
}
