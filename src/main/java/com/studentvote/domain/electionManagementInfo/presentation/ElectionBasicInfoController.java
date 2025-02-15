package com.studentvote.domain.electionManagementInfo.presentation;

import com.studentvote.domain.electionManagementInfo.application.ElectionBasicInfoService;
import com.studentvote.domain.electionManagementInfo.domain.ElectionBasicInfo;
import com.studentvote.domain.electionManagementInfo.dto.request.ElectionBasicInfoRequest;
import com.studentvote.domain.electionManagementInfo.dto.response.ElectionBasicInfoResponse;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import com.studentvote.global.payload.ResponseCustom;
import io.swagger.v3.oas.annotations.Operation;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ElectionBasicInfoController {

    private final ElectionBasicInfoService electionBasicInfoService;

    @PostMapping("/electionBasicInfo")
    @Operation(summary = "선거관리위원회의 구성 공고를 저장합니다", description = "(선거관리위원회 공고, 선거시행세칙 개정 공고, 후보자 모집 공고, 선거 세칙) 을 입력합니다")
    public ResponseCustom<ElectionBasicInfoResponse> uploadElectionInfo(@ModelAttribute ElectionBasicInfoRequest request) {
        ElectionBasicInfoResponse electionBasicInfoResponse = electionBasicInfoService.uploadElectionInfo(request);
        return ResponseCustom.OK(electionBasicInfoResponse);
    }

}
