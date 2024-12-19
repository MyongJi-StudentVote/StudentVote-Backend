package com.studentvote.domain.poster.dto.response;

import java.util.List;

public record GetAllPosterResponse(
        List<PosterResponse> posterList
) {
    public record PosterResponse(
            Long posterId,
            String posterName,
            String posterImage,
            Long userId,
            String candidateName
    ) {}
}
