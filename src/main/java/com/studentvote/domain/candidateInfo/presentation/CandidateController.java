package com.studentvote.domain.candidateInfo.presentation;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.candidateInfo.application.CandidateService;
import com.studentvote.domain.candidateInfo.domain.CandidateInfo;
import com.studentvote.domain.candidateInfo.dto.request.RegisterCandidateInfoRequest;
import com.studentvote.domain.candidateInfo.dto.response.CandidateInfoListResponse;
import com.studentvote.domain.candidateInfo.dto.response.RegisterCandidateInfoResponse;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping("/candidateInfo")
    @Operation(summary = "단과대별 후보자 기본 정보 입력", description = "입후보자 등록에 사용되는 API입니다. 선거유형, 선본명, 선본통신공간주소, 입후보자 공고 이미지, 로고이미지 입니다. 선거유형은 [단선 (UNCONTESTED_ELECTION) ,경선 (PRIMARY_ELECTION) String 값으로 둘중 하나 넣어주시면 됩니다], ")
    public ResponseCustom<RegisterCandidateInfoResponse> registerPoster(@AuthenticationPrincipal CustomUserDetails userDetails, @ModelAttribute RegisterCandidateInfoRequest request) {
        RegisterCandidateInfoResponse registerCandidateInfoResponse = candidateService.registerCandidate(userDetails, request);
        return ResponseCustom.OK(registerCandidateInfoResponse);
    }

    @GetMapping("/candidateInfo/{governanceId}")
    @Operation(summary = "단과대별 후보자 기본 정보 조회", description = "단과대학 타입을 입력하면 후보자 기본정보(선거유형, 선본명, 선본통신공간주소, 입후보자 공고 이미지, 로고이미지)를 조회합니다.")
    public ResponseCustom<CandidateInfoListResponse> getCandidateInfo(@PathVariable("governanceId") String governanceId) {
        CandidateInfoListResponse candidateInfo = candidateService.getCandidateInfo(governanceId);
        return ResponseCustom.OK(candidateInfo);
    }
}
