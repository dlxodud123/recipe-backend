package com.taeyoung.recipe.recipe_backend.service;

import com.taeyoung.recipe.recipe_backend.domain.member.Member;
import com.taeyoung.recipe.recipe_backend.domain.member.ProviderType;
import com.taeyoung.recipe.recipe_backend.domain.member.Role;
import com.taeyoung.recipe.recipe_backend.dto.member.request.SignupRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.UpdateRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindPasswordRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.request.find.FindUsernameRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.member.response.MemberResponseDto;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateEmailException;
import com.taeyoung.recipe.recipe_backend.global.exception.DuplicateUsernameException;
import com.taeyoung.recipe.recipe_backend.repository.member.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입(아이디 중복 확인) !!
    public void isUsernameDuplicated(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException("이미 사용중인 아이디입니다.");
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

    // 회원 탈퇴
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        memberRepository.delete(member);
    }

    // 회원 정보 수정
    public void updateMember(UpdateRequestDto updateRequestDto, Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        String encodedPassword = passwordEncoder.encode(updateRequestDto.getPassword());
        member.updateMember(encodedPassword, updateRequestDto.getEmail());
    }

    // username 찾기 !!
    public String findUsername(FindUsernameRequestDto findUsernameRequestDto) {
        Member findMember = memberRepository.findByNameAndPhone(findUsernameRequestDto.getName(), findUsernameRequestDto.getPhone())
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

    // email 찾기
//    public String findByEmailByUsernameAndPassword(String username, String password) {
//        Member findMember = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
//
//        if (!passwordEncoder.matches(password, findMember.getPassword())) {
//            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//        }
//        return findMember.getEmail();
//    }


    // test 전용
//    @Transactional(readOnly = true)
//    public MemberResponseDto getMyInfo(Long id){
//        Member findMember = memberRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));
//        return new MemberResponseDto(findMember.getUsername(), findMember.getEmail());
//    }
    @Transactional(readOnly = true)
    public Optional<Member> findByUsername(String username){
        return memberRepository.findByUsername(username);
    }
}
