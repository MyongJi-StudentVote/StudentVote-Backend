package com.studentvote.domain.vote.dto.request;

public record RegisterVoteRateRequest(
        int voteCount,
        double voteRate
) {
}
