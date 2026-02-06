package com.taeyoung.recipe.recipe_backend.controller;


import com.taeyoung.recipe.recipe_backend.dto.admin.request.AdminRoleChangeRequestDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminDashboardResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberDetailResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberResponseDto;
import com.taeyoung.recipe.recipe_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // 대시보드
    @GetMapping("/dashboard")
    public AdminDashboardResponseDto getDashboard() {
        return adminService.getDashboard();
    }

    // 전체 회원 조회
    @GetMapping("/members")
    public Page<AdminMemberResponseDto> getMembers(Pageable pageable) {
        return adminService.getMembers(pageable);
    }

    // 회원 상세 조회
    @GetMapping("/members/{memberId}")
    public AdminMemberDetailResponseDto getMemberDetail(@PathVariable Long memberId) {
        return adminService.getMemberDetail(memberId);
    }

    // 회원 삭제
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        adminService.deleteMember(memberId);

        return ResponseEntity.ok().body(Map.of("message", "회원 삭제 완료!"));
    }

    // 회원 권한 변경
    @PutMapping("/members/{memberId}")
    public ResponseEntity<?> updateMember(@PathVariable Long memberId, @RequestBody AdminRoleChangeRequestDto adminRoleChangeRequestDto) {
        adminService.changeMemberRole(memberId, adminRoleChangeRequestDto.getRole());

        return ResponseEntity.ok().body(Map.of("message", "회원 권한 변경 완료!"));
    }
}
