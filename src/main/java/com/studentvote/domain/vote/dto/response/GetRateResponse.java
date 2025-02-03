package com.studentvote.domain.vote.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;

public record GetRateResponse(
        LocalDateTime localDateTime,
        int voteCount,
        double voteRate
) {

    @Builder
    @QueryProjection
    public GetRateResponse(LocalDateTime localDateTime, int voteCount, double voteRate) {
        this.localDateTime = localDateTime;
        this.voteCount = voteCount;
        this.voteRate = voteRate;
    }
}
