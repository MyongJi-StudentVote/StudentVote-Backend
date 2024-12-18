package com.studentvote.domain.admin.presentation;

import com.studentvote.domain.admin.application.AdminService;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "Admin API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "가입 승인 대기 계정 조회", description = "가입 승인 대기 중인 계정 목록을 마스터 계정이 조회합니다.")
    @GetMapping
    public ResponseCustom<?> getAccountsWaitingForApproval(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseCustom.OK(adminService.getAccountsWaitingForApproval(userDetails));
    }
}
