package com.studentvote.domain.auth.dto.request;

public record SignUpRequest(
        String email,
        String password,
        String name,
        Long governanceId
) {
}
