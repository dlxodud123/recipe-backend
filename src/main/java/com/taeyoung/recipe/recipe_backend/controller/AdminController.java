package com.taeyoung.recipe.recipe_backend.controller;


import com.taeyoung.recipe.recipe_backend.dto.admin.response.AdminDashboardResponseDto;
import com.taeyoung.recipe.recipe_backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public AdminDashboardResponseDto getDashboard() {
        return adminService.getDashboard();
    }
}
