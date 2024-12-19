package com.studentvote.domain.candidateInfo.dto.request;

import com.studentvote.domain.candidateInfo.domain.ElectionType;
import org.springframework.web.multipart.MultipartFile;

public record RegisterCandidateInfoRequest(
        ElectionType electionType,
        String candidateName,
        String candidateContactAddress,
        MultipartFile candidateInfoImage,
        MultipartFile logoImage

) {
}
