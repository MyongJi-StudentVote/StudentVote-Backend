package com.studentvote.domain.vote.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public record GetRateResponse(
        LocalDateTime localDateTime,
        int voteCount,
        double voteRate
) {

    @QueryProjection
    public GetRateResponse(LocalDateTime localDateTime, int voteCount, double voteRate) {
        this.localDateTime = localDateTime;
        this.voteCount = voteCount;
        this.voteRate = voteRate;
    }
}
