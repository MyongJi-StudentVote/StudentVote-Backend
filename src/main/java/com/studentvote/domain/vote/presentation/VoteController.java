package com.studentvote.domain.vote.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.vote.application.VoteService;
import com.studentvote.domain.vote.dto.request.CreateVoteRequest;
import com.studentvote.domain.vote.dto.request.RegisterVoteRateRequest;
import com.studentvote.domain.vote.dto.response.GetRateResponse;
import com.studentvote.domain.vote.dto.response.GetResultResponse;
import com.studentvote.domain.vote.dto.response.GetVoteMetadataResponse;
import com.studentvote.global.payload.Message;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Vote", description = "Vote API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vote")
public class VoteController {

    private final VoteService voteService;

    @Operation(summary = "선거 메타데이터 등록", description = "선거 관련 정보를 등록합니다.")
    @PostMapping
    public ResponseCustom<Message> createVote(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute CreateVoteRequest createVoteRequest
    ) {
        return ResponseCustom.OK(voteService.createVote(userDetails, createVoteRequest));
    }

    @Operation(summary = "선거 메타데이터 조회", description = "선거 관련 정보를 조회합니다.")
    @GetMapping("/metadata")
    public ResponseCustom<GetVoteMetadataResponse> getVoteMetadata() {
        return ResponseCustom.OK(voteService.getVoteMetadata());
    }

    @Operation(summary = "선거 초기화", description = "선거 관련 데이터를 초기화힙니다.")
    @PostMapping("/reset")
    public ResponseCustom<Message> reset(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseCustom.OK(voteService.reset(userDetails));
    }

    @Operation(summary = "투표율 현황 등록", description = "투표 단과대 또는 학과 별 투표 현황을 등록합니다.")
    @PostMapping("/rate/{departmentId}")
    public ResponseCustom<Message> registerVoteRate(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long departmentId,
            @RequestBody RegisterVoteRateRequest registerVoteRateRequest
    ) {
        return ResponseCustom.OK(voteService.registerVoteRate(userDetails, departmentId, registerVoteRateRequest));
    }

    @Operation(summary = "투표율 조회", description = "선거(단과대/학과) 별 투표율을 조회합니다.")
    @GetMapping("/rate/{departmentId}")
    public ResponseCustom<Page<GetRateResponse>> getRate(
            @PathVariable Long departmentId,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseCustom.OK(voteService.getRate(departmentId, pageable));
    }

    @Operation(summary = "개표 결과 등록", description = "각 단과대/학과 별로 개표 결과를 등록합니다.")
    @PostMapping("/result")
    public ResponseCustom<Message> postResult(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseCustom.OK(voteService.postResult(userDetails, file));
    }

    @Operation(summary = "개표 결과 조회", description = "각 단과대/학과 별 개표 결과를 조회합니다.")
    @GetMapping("/result/{governanceId}")
    public ResponseCustom<GetResultResponse> getResult(@PathVariable Long governanceId) {
        return ResponseCustom.OK(voteService.getResult(governanceId));
    }
}
