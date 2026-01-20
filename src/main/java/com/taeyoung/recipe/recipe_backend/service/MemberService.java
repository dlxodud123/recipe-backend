package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.member.CustomUser;
import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.UpdateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindPasswordRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindUsernameRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.response.MyPageResponseDto;
import com.taeyoung.recipe.recipe_backend.global.exception.AlreadyLinkedAccountException;
import com.taeyoung.recipe.recipe_backend.global.exception.AlreadyUnlinkedAccountException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateEmailException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입(아이디 중복 확인) !!
    @Transactional(readOnly = true)
    public void isUsernameDuplicated(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException("이미 사용중인 아이디입니다.");
        }
    }

    // 회원가입(이메일 중복 확인) !!
    @Transactional(readOnly = true)
    public void isEmailDuplicated(String email) {
        if (memberRepository.existsByEmailAndProvider(email, ProviderType.LOCAL)) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }

    // 회원가입 !!
    public Member registerMember(SignupRequestDto signupRequestDto){
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        return memberRepository.save(new Member(
                ProviderType.LOCAL,
                Role.USER,
                signupRequestDto.getUsername(),
                encodedPassword,
                signupRequestDto.getEmail(),
                signupRequestDto.getName(),
                signupRequestDto.getPhone(),
                signupRequestDto.getAgeConsent(),
                signupRequestDto.getZipcode(),
                signupRequestDto.getAddress(),
                signupRequestDto.getDetailAddress(),
                signupRequestDto.getBirthDate(),
                signupRequestDto.getGender()
        ));
    }

    // 회원 정보 조회 !!
    @Transactional(readOnly = true)
    public MyPageResponseDto getMyInfo(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        boolean isLinked;

        if (member.getProvider() == ProviderType.LOCAL) {
            // 로컬 로그인 → 소셜
            isLinked = memberRepository.existsByLinkedMemberId(member.getId());
        } else {
            // 소셜 로그인 → 로컬
            isLinked = member.getLinkedMemberId() != null;
        }

        return MyPageResponseDto.from(member, isLinked);
    }

    // 회원 정보 수정 !!
    public void updateMember(UpdateRequestDto updateRequestDto, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        member.updateMember(
                updateRequestDto.getName(),
                updateRequestDto.getZipcode(),
                updateRequestDto.getAddress(),
                updateRequestDto.getDetailAddress(),
                updateRequestDto.getGender()
        );
    }

    // 회원 탈퇴 !!
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        memberRepository.delete(member);
    }

    // 회원 연동 !!
    public void linkMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (member.getProvider() == ProviderType.LOCAL) {
            linkSocial(member);
        } else {
            linkLocal(member);
        }
    }

    // 회원 연동 해제
    public void deleteLinkMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (member.getProvider() == ProviderType.LOCAL) {
            deleteLinkSocial(member);
        } else {
            deleteLinkLocal(member);
        }
    }

    // username 찾기 !!
    @Transactional(readOnly = true)
    public String findUsername(FindUsernameRequestDto findUsernameRequestDto) {
        Member findMember = memberRepository.findByNameAndEmail(findUsernameRequestDto.getName(), findUsernameRequestDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        return findMember.getUsername();
    }

    // password 찾기 !!
    public String findPassword(FindPasswordRequestDto findPasswordRequestDto) {
        Member findMember = memberRepository.findByNameAndUsername(findPasswordRequestDto.getName(), findPasswordRequestDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        // 임시 비밀번호 생성 (UUID 앞 8자리)
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        findMember.setRandomPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(findMember);

        return tempPassword;
    }

    // test 전용 !!
    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username){
        return memberRepository.findByUsername(username);
    }


    // 소셜계정으로 연동하려고 하는 경우 !!
    private void linkSocial(Member local) {
        Member social = memberRepository.findByEmailAndProviderNot(local.getEmail(), ProviderType.LOCAL)
                .orElseThrow(() -> new EntityNotFoundException("연동할 소셜 계정이 없습니다."));

        // 이미 연동된 계정입니다.
        if (social.getLinkedMemberId() != null) {
            throw new AlreadyLinkedAccountException();
        }

        social.link(local.getId());
    }
    // 로컬계정으로 연동하려고 하는 경우 !!
    private void linkLocal(Member social) {
        // 이미 연동된 계정입니다.
        if (social.getLinkedMemberId() != null) {
            throw new AlreadyLinkedAccountException();
        }

        Member local = memberRepository.findByEmailAndProvider(social.getEmail(), ProviderType.LOCAL)
                .orElseThrow(() -> new EntityNotFoundException("연동할 로컬 계정이 없습니다."));

        social.link(local.getId());
    }

    // 로컬계정으로 연동해제 하려고 하는 경우 !!
    @Transactional
    private void deleteLinkSocial(Member local) {
        Member social = memberRepository.findByLinkedMemberId(local.getId())
                .orElseThrow(() -> new EntityNotFoundException("연동된 소셜 계정이 없습니다."));

        social.unlink();
    }
    // 소셜계정으로 연동해제 하려고 하는 경우 !!
    @Transactional
    private void deleteLinkLocal(Member social) {
        if (social.getLinkedMemberId() == null) {
            throw new AlreadyUnlinkedAccountException();
        }

        social.unlink();
    }
}
