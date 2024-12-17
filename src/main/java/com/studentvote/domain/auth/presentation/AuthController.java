package com.studentvote.domain.auth.presentation;

import com.studentvote.domain.auth.application.AuthService;
import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.global.payload.Message;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authorization", description = "Authorization API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "신규 사용자가 회원가입을 요청합니다.")
    @PostMapping("/sign-up")
    public ResponseCustom<Message> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseCustom.OK(authService.signUp(signUpRequest));
    }
}
