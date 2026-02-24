package com.taeyoung.recipe.recipe_backend.member;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateEmailException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
import com.taeyoung.recipe.recipe_backend.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MemberSignupServiceTest {

    @Autowired private MemberService memberService;

    private SignupRequestDto createSignupDto(String username, String email) {
        return new SignupRequestDto(
            username,
            "testPassword",
            email,
            "이태영",
            "01038585430",
            true,
            "12345",
            "서울시 강남구",
            "101동 202호",
            LocalDate.of(2001, 12, 13),
            Gender.F
        );
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_success() {
        // given: 테스트용 DTO 생성
        SignupRequestDto request = createSignupDto("testUsername", "test@test.com");

        // when
        Member member = memberService.registerMember(request);

        // then
        assertThat(member.getId()).isNotNull();
        assertThat(member.getUsername()).isEqualTo("testUsername");
        assertThat(member.getEmail()).isEqualTo("test@test.com");
    }
    @Test
    @DisplayName("아이디 사용 불가 - 중복")
    void username_duplicate() {
        // given
        memberService.registerMember(
                createSignupDto("taeyoung", "a@test.com"));

        // when & then
        assertThatThrownBy(() ->
                memberService.isUsernameDuplicated("taeyoung")
        )
                .isInstanceOf(DuplicateUsernameException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }
    @Test
    @DisplayName("이메일 사용 불가 - 중복")
    void email_duplicate() {
        // given
        memberService.registerMember(
                createSignupDto("user1", "test@test.com"));

        // when & then
        assertThatThrownBy(() ->
                memberService.isEmailDuplicated("test@test.com")
        )
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("이미 사용중인 이메일입니다.");
    }






//    @Test
//    @DisplayName("아이디 사용 가능")
//    void username_available() {
//        // given
//        String username = "taeyoung";
//
//        // when & then
//        assertThatCode(() ->
//                memberService.isUsernameDuplicated(username)
//        ).doesNotThrowAnyException();
//    }
//    @Test
//    @DisplayName("이메일 사용 가능")
//    void email_available() {
//        // given
//        String email = "test@test.com";
//
//        // when & then
//        assertThatCode(() ->
//                memberService.isEmailDuplicated(email)
//        ).doesNotThrowAnyException();
//    }
}
