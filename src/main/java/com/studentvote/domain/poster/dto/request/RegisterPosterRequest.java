package com.studentvote.domain.poster.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record RegisterPosterRequest(
        String posterName,
        MultipartFile posterImage

) {
}
