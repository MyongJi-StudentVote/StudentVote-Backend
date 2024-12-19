package com.studentvote.domain.admin.presentation;

import com.studentvote.domain.admin.application.AdminService;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.global.payload.Message;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseCustom<?> getAccountsWaitingForApproval(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseCustom.OK(adminService.getAccountsWaitingForApproval(userDetails));
    }

    @Operation(summary = "가입 승인", description = "가입 대기중인 선택한 유저에 대한 가입을 승인합니다.")
    @PostMapping("/admit/{userId}")
    public ResponseCustom<Message> admitUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long userId
    ) {
        return ResponseCustom.OK(adminService.admitUser(userDetails, userId));
    }
}
