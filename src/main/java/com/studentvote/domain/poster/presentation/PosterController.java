package com.studentvote.domain.poster.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.application.PosterService;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.RegisterPosterResponse;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class PosterController {

    private final PosterService posterService;

    @PostMapping("/poster")
    public ResponseCustom<RegisterPosterResponse> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterPosterRequest request) {
        Poster poster = posterService.registerPoster(userDetails, request);
        return ResponseCustom.OK(new RegisterPosterResponse(poster.getId(), poster.getPosterName(), poster.getPosterImage()));
    }

    @DeleteMapping("poster/{posterId}")
    public ResponseCustom<?> deletePoster(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long posterId) {
        Poster poster = posterService.deletePoster(userDetails, posterId);
        return ResponseCustom.OK(poster.getPosterName());
    }
}
