package com.studentvote.domain.admin.application;

import com.studentvote.domain.admin.dto.response.AccountsWaitingForApprovalResponse;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private static final String MASTER_USERNAME = "ADMIN";

    private final UserRepository userRepository;

    public List<AccountsWaitingForApprovalResponse> getAccountsWaitingForApproval(CustomUserDetails userDetails) {

        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        Optional<List<User>> allByApprovalStatus = userRepository.findAllByApprovalStatus(ApprovalStatus.PENDING);

        if (!allByApprovalStatus.isPresent()) {
            return new ArrayList<>();
        }

        List<AccountsWaitingForApprovalResponse> accountsWaitingForApprovalResponses = allByApprovalStatus.get()
                .stream()
                .map(user -> new AccountsWaitingForApprovalResponse(
                        user.getId(),
                        user.getUsername(),
                        "",
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return accountsWaitingForApprovalResponses;
    }
}
