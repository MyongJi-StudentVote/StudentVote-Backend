package com.studentvote.domain.auth.presentation;

import com.studentvote.domain.auth.application.AuthService;
import com.studentvote.domain.auth.dto.request.SignUpRequest;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseCustom<?> signUp(SignUpRequest signUpRequest) {
        return ResponseCustom.OK(authService.signUp(signUpRequest));
    }
}
