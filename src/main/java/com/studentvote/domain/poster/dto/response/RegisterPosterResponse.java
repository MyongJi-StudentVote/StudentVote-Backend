package com.studentvote.domain.poster.dto.response;

public record RegisterPosterResponse(
        Long posterId,
        String posterName,
        String posterImageUrl

) {
}
