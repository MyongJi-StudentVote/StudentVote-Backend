package com.studentvote.domain.electionManagementInfo.presentation;

import com.studentvote.domain.electionManagementInfo.application.ElectionBasicInfoService;
import com.studentvote.domain.electionManagementInfo.domain.ElectionBasicInfo;
import com.studentvote.domain.electionManagementInfo.dto.request.ElectionBasicInfoRequest;
import com.studentvote.domain.electionManagementInfo.dto.response.ElectionBasicInfoResponse;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import com.studentvote.global.payload.ResponseCustom;
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
@RequestMapping("/api/electionBasicInfo")
public class ElectionBasicInfoController {

    private final ElectionBasicInfoService electionBasicInfoService;

    @PostMapping("/upload")
    public ResponseCustom<ElectionBasicInfoResponse> uploadElectionInfo(
            @ModelAttribute ElectionBasicInfoRequest request) {
        ElectionBasicInfoResponse electionBasicInfoResponse = electionBasicInfoService.uploadElectionInfo(request);
        return ResponseCustom.OK(electionBasicInfoResponse);
    }

}
