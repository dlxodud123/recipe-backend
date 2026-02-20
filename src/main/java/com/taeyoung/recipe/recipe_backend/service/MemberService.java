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
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateEmailException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (member.getLinkedMemberId() == null) {
            isLinked = false;
        } else {
            isLinked = true;
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
        // 삭제할 회원 가져오기
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        // 연동된 멤버 링크 끊기
        Member linkedMember = memberRepository.findByLinkedMemberId(id).orElse(null);

        if (linkedMember != null) {
            linkedMember.unlink();  // linkedMemberId = null
            memberRepository.save(linkedMember); // 변경을 DB에 반영
        }

        // 실제 회원 삭제
        memberRepository.delete(member);
    }
        
    // 회원 연동 !!
    public void linkMember(Long id){
        Member localMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        Member socialMember = memberRepository.findByEmailAndProvider(localMember.getEmail(), ProviderType.GOOGLE)
                .orElseThrow(() -> new EntityNotFoundException("연동할 회원이 존재하지 않습니다."));

        localMember.link(socialMember.getId());
    }

    // 회원 연동 해제 !!
    public void deleteLinkMember(Long id){
        Member localMember = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        localMember.unlink();
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
}
