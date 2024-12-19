package com.studentvote.domain.poster.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.poster.application.PosterService;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.GetAllPosterResponse;
import com.studentvote.domain.poster.dto.response.GetPosterResponse;
import com.studentvote.domain.poster.dto.response.RegisterPosterResponse;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class PosterController {

    private final PosterService posterService;

    @PostMapping("/poster")
    @Operation(summary = "입후보자의 게시물(선거 공약)을 등록합니다", description = "(게시글 제목, 공약 이미지)을 입력합니다")
    public ResponseCustom<RegisterPosterResponse> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterPosterRequest request) {
        Poster poster = posterService.registerPoster(userDetails, request);
        return ResponseCustom.OK(new RegisterPosterResponse(poster.getId(), poster.getPosterName(), poster.getPosterImage()));
    }

    @DeleteMapping("poster/{posterId}")
    @Operation(summary = "입후보자의 게시물(선거 공약)을 삭제합니다", description = "게시물의 ID를 입력합니다")
    public ResponseCustom<?> deletePoster(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long posterId) {
        Poster poster = posterService.deletePoster(userDetails, posterId);
        return ResponseCustom.OK(poster.getPosterName());
    }

    @GetMapping("/poster/{posterId}")
    @Operation(summary = "입후보자의 게시물(선거 공약)을 게시물 ID로 조회합니다", description = "게시물의 ID를 입력합니다")
    public ResponseCustom<?> getPosterByPosterId(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long posterId) {
        Poster poster = posterService.getPosterById(userDetails, posterId);
        return ResponseCustom.OK(new GetPosterResponse(poster.getId(), poster.getPosterName(), poster.getPosterImage()));
    }

    @GetMapping("/poster/governance/{governanceType}")
    @Operation(summary = "입후보자의 게시물(선거 공약)을 단과대학 별로 조회합니다", description = "단과대학별로 모든 게시물(공약)을 userId, 후보자의 선거본부 이름과 함께 List로 제공합니다 ")
    public ResponseCustom<GetAllPosterResponse> getAllPosterByGovernance(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String governanceType) {
        GetAllPosterResponse posterList = posterService.getAllPosterByGovernance(userDetails, governanceType);
        return ResponseCustom.OK(posterList);
    }

}
