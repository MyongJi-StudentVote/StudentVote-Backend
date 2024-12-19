package com.studentvote.domain.vote.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record CreateVoteRequest(
        String voteName,
        String voteDateTime,
        String voteDescription,
        MultipartFile file
) {
}
