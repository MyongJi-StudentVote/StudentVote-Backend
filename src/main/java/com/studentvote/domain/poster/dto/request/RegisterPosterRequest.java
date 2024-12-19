package com.studentvote.domain.poster.dto.request;

import com.studentvote.domain.poster.domain.ElectionType;
import org.springframework.web.multipart.MultipartFile;

public record RegisterPosterRequest(

        ElectionType electionType,
        String candidateName,
        String candidateContactAddress,
        MultipartFile candidateInfoImage,
        MultipartFile logoImage

) {
}
