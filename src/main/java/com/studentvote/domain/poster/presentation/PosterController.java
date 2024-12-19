package com.studentvote.domain.poster.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.application.PosterService;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class PosterController {

    private final PosterService posterService;

    @PostMapping("/poster")
    public ResponseCustom<Poster> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterPosterRequest request) {
        Poster poster = posterService.registerPoster(userDetails, request);
        return ResponseCustom.OK(poster);
    }

}
