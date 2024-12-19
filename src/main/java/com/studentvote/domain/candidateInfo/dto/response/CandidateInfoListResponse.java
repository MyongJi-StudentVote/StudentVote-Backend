package com.studentvote.domain.candidateInfo.dto.response;

import lombok.Builder;

import java.util.List;

public record CandidateInfoListResponse(
        List<CandidateInfoResponse> candidateInfoList

        ) {

    @Builder
    public record CandidateInfoResponse(
            Long id,
            String candidateName,
            String candidateContactAddress,
            String candidateInfoImage,
            String logoImage

    ) {
    }

}
