package com.studentvote.domain.candidateInfo.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.candidateInfo.application.CandidateService;
import com.studentvote.domain.candidateInfo.domain.CandidateInfo;
import com.studentvote.domain.candidateInfo.dto.request.RegisterCandidateInfoRequest;
import com.studentvote.domain.candidateInfo.dto.response.CandidateInfoListResponse;
import com.studentvote.domain.candidateInfo.dto.response.RegisterCandidateInfoResponse;
import com.studentvote.global.payload.ResponseCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/candidateInfo/")
    public ResponseCustom<RegisterCandidateInfoResponse> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterCandidateInfoRequest request) {
        RegisterCandidateInfoResponse registerCandidateInfoResponse = candidateService.registerCandidate(userDetails, request);
        return ResponseCustom.OK(registerCandidateInfoResponse);
    }


    @GetMapping("/candidateInfo/{governance}")
    public ResponseCustom<CandidateInfoListResponse> getCandidateInfo(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("governance") String governanceType) {
        CandidateInfoListResponse candidateInfo = candidateService.getCandidateInfo(userDetails, governanceType);
        return ResponseCustom.OK(candidateInfo);
    }


}
