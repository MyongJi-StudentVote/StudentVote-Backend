package com.studentvote.domain.candidateInfo.application;

import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.candidateInfo.domain.CandidateInfo;
import com.studentvote.domain.candidateInfo.domain.CandidateInfoRepository;
import com.studentvote.domain.candidateInfo.dto.request.RegisterCandidateInfoRequest;
import com.studentvote.domain.candidateInfo.dto.response.CandidateInfoListResponse;
import com.studentvote.domain.candidateInfo.dto.response.RegisterCandidateInfoResponse;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CandidateService {

    private final CandidateInfoRepository candidateInfoRepository;
    private final S3Service s3Service;

    @Transactional
    public RegisterCandidateInfoResponse registerCandidate(CustomUserDetails userDetails, RegisterCandidateInfoRequest request) {
        validateUploadRequest(request);
        User user = userDetails.user();
        if (user.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new DefaultException(ErrorCode.STATUS_NOT_APPROVED);
        }
        String candidateInfoImageName = makeFileName(user, "candidateInfoImage");
        String logoInfoImageName = makeFileName(user, "logoInfoImage");
        String candidateInfoImageUrl = s3Service.uploadImageToS3(request.candidateInfoImage(), candidateInfoImageName);
        String logoImageUrl = s3Service.uploadImageToS3(request.logoImage(), logoInfoImageName);
        CandidateInfo save = candidateInfoRepository.save(new CandidateInfo(null, request.candidateName(), request.candidateContactAddress(), candidateInfoImageUrl, logoImageUrl, request.electionType(), user));
        return new RegisterCandidateInfoResponse(save.getId());
    }

    @Transactional(readOnly = true)
    public CandidateInfoListResponse getCandidateInfo(CustomUserDetails userDetails,String governanceType) {
        User user = userDetails.user();
        List<CandidateInfo> candidateInfoList = candidateInfoRepository
                .findAllCandidateByGovernanceType(user.getId(), governanceType);

        if (candidateInfoList.isEmpty()) throw new DefaultException(ErrorCode.CANDIDATE_INFO_NOT_FOUND);

        List<CandidateInfoListResponse.CandidateInfoResponse> candidateInfoResponseList =
                candidateInfoList.stream()
                        .map(candidateInfo -> new CandidateInfoListResponse.CandidateInfoResponse(
                                candidateInfo.getId(),
                                candidateInfo.getElectionType(),
                                candidateInfo.getCandidateName(),
                                candidateInfo.getCandidateContactAddress(),
                                candidateInfo.getCandidateInfoImage(),
                                candidateInfo.getLogoImage()
                        ))
                        .toList();
        return new CandidateInfoListResponse(candidateInfoResponseList);
    }

    private String makeFileName(User user, String option) {
        String fileName = "candidateInfo/" + user.getId() + "/";
        if (option.equals("candidateInfoImage")) {
            fileName += "candidateInfoImage";
        } else if (option.equals("logoInfoImage")) {
            fileName += "logoInfoImage";
        }
        return fileName;
    }

    private void validateUploadRequest(RegisterCandidateInfoRequest request) {
        if (request == null) {
            throw new DefaultException(ErrorCode.INVALID_PARAMETER);
        }
        if (request.candidateInfoImage() == null) {
            throw new DefaultException(ErrorCode.INVALID_CANDIDATE_INFO_IMAGE);
        }
        if (request.logoImage() == null) {
            throw new DefaultException(ErrorCode.INVALID_LOGO_IMAGE);
        }
    }


}
