package com.studentvote.domain.vote.dto.response;

import lombok.Builder;

public record GetVoteMetadataResponse(
        String voteName,
        String voteDateTime,
        String voteDescription,
        String fileUrl
) {

    @Builder
    public GetVoteMetadataResponse(String voteName, String voteDateTime, String voteDescription, String fileUrl) {
        this.voteName = voteName;
        this.voteDateTime = voteDateTime;
        this.voteDescription = voteDescription;
        this.fileUrl = fileUrl;
    }
}
