package com.taeyoung.recipe.recipe_backend.member;

import com.taeyoung.recipe.recipe_backend.domain.member.Gender;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.global.exception.AlreadyLinkedAccountException;
import com.taeyoung.recipe.recipe_backend.global.exception.AlreadyUnlinkedAccountException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import com.taeyoung.recipe.recipe_backend.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
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
public class MemberEmailLinkServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    private Long localMemberId;
    private Long googleMemberId;

    @BeforeEach
    void setUp() {
        SignupRequestDto request = new SignupRequestDto(
                "testUser",
                "testPassword",
                "test@test.com",
                "이태영",
                "01038585430",
                true,
                "12345",
                "서울시 강남구",
                "101동 202호",
                LocalDate.of(2001, 12, 13),
                Gender.F
        );
        localMemberId = memberService.registerMember(request).getId();

        Member googleMember = new Member(
                ProviderType.GOOGLE,
                Role.USER,
                "googleUser",
                "googlePassword",
                "test@test.com",
                "구글사용자",
                "01022223333",
                true,
                "12345",
                "서울시 강남구",
                "101동 101호",
                LocalDate.of(2000, 1, 1),
                Gender.M
        );
        googleMember.setProviderId("google-12345");
        googleMemberId = memberRepository.save(googleMember).getId();
    }

    @Test
    @DisplayName("이메일 연동 성공")
    void linkEmail_success() {
        // when & then    
        assertThatCode(() -> memberService.linkMember(localMemberId))
                .doesNotThrowAnyException();

        Member localMember = memberRepository.findById(localMemberId).orElseThrow();
        assertThat(localMember.getLinkedMemberId()).isEqualTo(googleMemberId);
    }
    @Test
    @DisplayName("이메일 연동 해제 성공")
    void unlinkEmail_success() {
        // given
        memberService.linkMember(localMemberId);

        // when & then
        assertThatCode(() -> memberService.deleteLinkMember(localMemberId))
                .doesNotThrowAnyException();

        Member localMember = memberRepository.findById(localMemberId).orElseThrow();
        assertThat(localMember.getLinkedMemberId()).isNull();
    }
    @Test
    @DisplayName("이미 연동된 계정 연동 시도")
    void linkEmail_alreadyLinked() {
        // given
        memberService.linkMember(localMemberId);

        // when & then
        assertThatThrownBy(() -> memberService.linkMember(localMemberId))
                .isInstanceOf(AlreadyLinkedAccountException.class)
                .hasMessage("이미 연동된 계정입니다.");
    }
    @Test
    @DisplayName("이미 해제된 계정 재해제 시도")
    void unlinkEmail_alreadyUnlinked() {
        // given
        memberService.linkMember(localMemberId);
        memberService.deleteLinkMember(localMemberId);

        // when & then
        assertThatThrownBy(() -> memberService.deleteLinkMember(localMemberId))
                .isInstanceOf(AlreadyUnlinkedAccountException.class)
                .hasMessage("이미 연동 해제된 계정입니다.");
    }
}
