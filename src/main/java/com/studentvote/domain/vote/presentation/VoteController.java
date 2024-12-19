package com.studentvote.domain.vote.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.vote.application.VoteService;
import com.studentvote.domain.vote.dto.request.CreateVoteRequest;
import com.studentvote.global.payload.Message;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "선거 초기화", description = "선거 관련 데이터를 초기화힙니다.")
    @PostMapping("/reset")
    public ResponseCustom<Message> reset(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseCustom.OK(voteService.reset(userDetails));
    }
}
