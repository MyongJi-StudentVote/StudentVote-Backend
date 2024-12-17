package com.studentvote.domain.auth.dto.request;

public record SignInRequest(
        String email,
        String password
) {
}
