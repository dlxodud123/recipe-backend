package com.taeyoung.recipe.recipe_backend.controller;


import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminDashboardResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberDetailResponseDto;
import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminMemberResponseDto;
import com.taeyoung.recipe.recipe_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<AdminMemberResponseDto> getMembers() {
        return adminService.getMembers();
    }

    // 회원 상세 조회
    @GetMapping("/members/{memberId}")
    public AdminMemberDetailResponseDto getMemberDetail(@PathVariable Long memberId) {
        return adminService.getMemberDetail(memberId);
    }
}
