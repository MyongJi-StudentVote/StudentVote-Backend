package com.studentvote.domain.poster.application;

import com.amazonaws.services.s3.AmazonS3;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.electionManagementInfo.dto.request.ElectionBasicInfoRequest;
import com.studentvote.domain.poster.domain.ElectionType;
import com.studentvote.domain.poster.domain.Poster;
import com.studentvote.domain.poster.domain.PosterRepository;
import com.studentvote.domain.poster.dto.request.RegisterPosterRequest;
import com.studentvote.domain.poster.dto.response.RegisterPosterResponse;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.global.config.s3.S3Service;
import com.studentvote.global.error.DefaultException;
import com.studentvote.global.payload.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PosterService {

    private final PosterRepository posterRepository;
    private final S3Service s3Service;

    public RegisterPosterResponse registerPoster(CustomUserDetails userDetails, RegisterPosterRequest request) {
        validateUploadRequest(request);
        User user = userDetails.user();
        if (user.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new DefaultException(ErrorCode.STATUS_NOT_APPROVED);
        }
        String candidateInfoImageName = makeFileName(user, "candidateInfoImage");
        String logoInfoImageName = makeFileName(user, "logoInfoImage");
        String candidateInfoImageUrl = s3Service.uploadImageToS3(request.candidateInfoImage(), candidateInfoImageName);
        String logoImageUrl = s3Service.uploadImageToS3(request.logoImage(), logoInfoImageName);
        Poster save = posterRepository.save(new Poster(null, request.candidateName(), request.candidateContactAddress(), candidateInfoImageUrl, logoImageUrl, request.electionType(), user));
        return new RegisterPosterResponse(save.getId());
    }

    private String makeFileName(User user, String option) {
        String fileName = "posters/" + user.getId() + "/";
        if (option == "candidateInfoImage") {
            fileName += "candidateInfoImage";
        } else if (option == "logoInfoImage") {
            fileName += "logoInfoImage";
        }
        return fileName;
    }

    private void validateUploadRequest(RegisterPosterRequest request) {
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
