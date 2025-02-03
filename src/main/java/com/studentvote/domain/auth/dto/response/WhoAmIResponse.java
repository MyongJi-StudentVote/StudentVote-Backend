package com.studentvote.domain.auth.dto.response;

import lombok.Builder;

public record WhoAmIResponse(
        Long userId,
        String email,
        String userName,
        Long governanceId,
        String governanceName
) {

    @Builder
    public WhoAmIResponse(Long userId, String email, String userName, Long governanceId, String governanceName) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.governanceId = governanceId;
        this.governanceName = governanceName;
    }
}
