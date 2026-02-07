//package com.taeyoung.recipe.recipe_backend.service;
//
//import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
//import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
//import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindPasswordRequestDto;
//import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindUsernameRequestDto;
//import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
//import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//public class MemberServiceTest {
//
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private MemberService memberService;
//    @Autowired private PasswordEncoder passwordEncoder;
//
//    @BeforeEach
//    public void before() {
//        SignupRequestDto signupRequestDto1 = new SignupRequestDto("user1", "password1", "name1", "01012345678", true, "asdf", "zxcv", "qwer", LocalDate.parse("2001-12-13"), Gender.M);
//        SignupRequestDto signupRequestDto2 = new SignupRequestDto("user2", "password2", "name2", "01012345678", true, "asdf", "zxcv", "qwer", LocalDate.parse("2001-12-13"), Gender.M);
//
//        memberService.registerMember(signupRequestDto1);
//        memberService.registerMember(signupRequestDto2);
//    }
//
//    @Test
//    public void signupId() {
//        // when, then
////        assertThat(memberService.isUsernameDuplicated("user3")).isEqualTo("ok");
//        assertThatThrownBy(() ->
//                memberService.isUsernameDuplicated("user1")
//        )
//                .isInstanceOf(DuplicateUsernameException.class)
//                .hasMessage("이미 사용중인 아이디입니다.");
//    }
//
//    @Test
//    public void signup() {
//        // when, then
//        assertThat(memberService.findByUsername("user1")).isPresent();
//        assertThat(memberService.findByUsername("user3")).isNotPresent();
//    }
//
//    @Test
//    public void findUsername() {
//        // given
//        FindUsernameRequestDto findUsernameRequestDto1 = new FindUsernameRequestDto("name1", "01012345678");
//        FindUsernameRequestDto findUsernameRequestDto2 = new FindUsernameRequestDto("name11", "01012345678");
//
//        // when, then
//        assertThat(memberService.findUsername(findUsernameRequestDto1)).isEqualTo("user1");
//        assertThatThrownBy(() ->
//                memberService.findUsername(findUsernameRequestDto2)
//        )
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("회원이 존재하지 않습니다.");
//    }
//
//    @Test
//    public void findPassword() {
//        // given
//        FindPasswordRequestDto findPasswordRequestDto1 = new FindPasswordRequestDto("name1", "user1");
//        FindPasswordRequestDto findPasswordRequestDto2 = new FindPasswordRequestDto("name12", "user1");
//
//        // when, then
//        assertThat(memberService.findPassword(findPasswordRequestDto1).length()).isEqualTo(8);
//        assertThatThrownBy(() ->
//                memberService.findPassword(findPasswordRequestDto2)
//        )
//                .isInstanceOf(EntityNotFoundException.class)
//                .hasMessage("회원이 존재하지 않습니다.");
//    }
//}
