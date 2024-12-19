package com.studentvote.domain.poster.dto.response;

public record GetPosterResponse(
        Long posterId,
        String posterName,
        String posterImage

) {
}
