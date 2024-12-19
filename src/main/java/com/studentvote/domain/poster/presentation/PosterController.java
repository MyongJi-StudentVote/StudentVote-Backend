package com.studentvote.domain.poster.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.application.PosterService;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.RegisterPosterResponse;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PosterController {

    private final PosterService posterService;

    @PostMapping("/poster/")
    public ResponseCustom<RegisterPosterResponse> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterPosterRequest request) {
        RegisterPosterResponse registerPosterResponse = posterService.registerPoster(userDetails, request);
        return ResponseCustom.OK(registerPosterResponse);
    }

}
