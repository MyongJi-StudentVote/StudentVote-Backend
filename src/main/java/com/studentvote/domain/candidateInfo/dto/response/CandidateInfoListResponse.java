package com.studentvote.domain.candidateInfo.dto.response;

import com.studentvote.domain.candidateInfo.domain.ElectionType;
import lombok.Builder;

import java.util.List;

public record CandidateInfoListResponse(
        List<CandidateInfoResponse> candidateInfoList

        ) {

    @Builder
    public record CandidateInfoResponse(
            Long id,
            ElectionType electionType,
            String candidateName,
            String candidateContactAddress,
            String candidateInfoImage,
            String logoImage

    ) {
    }

}
