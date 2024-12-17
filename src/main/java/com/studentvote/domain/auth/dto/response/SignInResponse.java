package com.studentvote.domain.auth.dto.response;

import com.studentvote.domain.user.domain.Role;
import lombok.Builder;

public record SignInResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Role role
) {

    @Builder
    public SignInResponse(String accessToken, String refreshToken, String tokenType, Role role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
        this.role = role;
    }
}