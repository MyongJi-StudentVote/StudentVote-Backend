package com.studentvote.domain.admin.dto.response;

import java.time.LocalDateTime;

public record AccountsWaitingForApprovalResponse(
        Long userId,
        String username,
        String department,
        LocalDateTime requestDate
) {
}
