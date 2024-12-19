package com.studentvote.domain.vote.dto.response;

import lombok.Builder;

public record GetResultResponse(
        String departmentName,
        String resultImageUrl
) {

    @Builder
    public GetResultResponse(String departmentName, String resultImageUrl) {
        this.departmentName = departmentName;
        this.resultImageUrl = resultImageUrl;
    }
}
