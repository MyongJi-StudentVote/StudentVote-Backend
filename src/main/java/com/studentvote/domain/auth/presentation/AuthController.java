package com.studentvote.domain.auth.presentation;

import com.studentvote.domain.auth.application.AuthService;
import com.studentvote.domain.auth.dto.request.SignInRequest;
import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.auth.dto.response.SignInResponse;
import com.studentvote.domain.auth.dto.response.WhoAmIResponse;
import com.studentvote.global.payload.Message;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "로그인", description = "사용자가 로그인을 요청합니다.")
    @PostMapping("/sign-in")
    public ResponseCustom<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseCustom.OK(authService.signIn(signInRequest));
    }

    @Operation(summary = "내 정보 조회", description = "사용자가 자신의 정보를 조회합니다.")
    @GetMapping("/whoAmI")
    public ResponseCustom<WhoAmIResponse> whoAmI(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseCustom.OK(authService.whoAmI(userDetails));
    }
}
