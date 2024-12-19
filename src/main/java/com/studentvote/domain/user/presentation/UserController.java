package com.studentvote.domain.user.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.user.application.UserService;
import com.studentvote.domain.user.dto.response.CandidateInfoListResponse;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/candidateInfo")
    public ResponseCustom<CandidateInfoListResponse> getCandidateInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {


        return ResponseCustom.OK();
    }

}
