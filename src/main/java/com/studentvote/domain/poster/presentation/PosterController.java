package com.studentvote.domain.poster.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.application.PosterService;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.GetAllPosterResponse;
import com.studentvote.domain.poster.dto.response.GetPosterResponse;
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

    @GetMapping("/poster/{posterId}")
    public ResponseCustom<?> getPosterByPosterId(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long posterId) {
        Poster poster = posterService.getPosterById(userDetails, posterId);
        return ResponseCustom.OK(new GetPosterResponse(poster.getId(), poster.getPosterName(), poster.getPosterImage()));
    }

    @GetMapping("/poster/governance/{governanceType}")
    public ResponseCustom<GetAllPosterResponse> getAllPosterByGovernance(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String governanceType) {
        GetAllPosterResponse posterList = posterService.getAllPosterByGovernance(userDetails, governanceType);
        return ResponseCustom.OK(posterList);
    }

}
