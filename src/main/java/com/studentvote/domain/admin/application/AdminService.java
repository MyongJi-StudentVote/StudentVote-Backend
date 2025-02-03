package com.studentvote.domain.admin.application;

import com.studentvote.domain.admin.dto.response.AccountsWaitingForApprovalResponse;
import com.studentvote.domain.admin.exception.AlreadyApprovedUser;
import com.studentvote.domain.auth.dto.response.CustomUserDetails;
import com.studentvote.domain.user.domain.ApprovalStatus;
import com.studentvote.domain.user.domain.User;
import com.studentvote.domain.user.domain.repository.UserRepository;
import com.studentvote.global.payload.Message;
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
                        user.getGovernance().getGovernanceName().isEmpty() ? user.getGovernance().getGovernanceType() : user.getGovernance().getGovernanceName(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return accountsWaitingForApprovalResponses;
    }//

    @Transactional
    public Message admitUser(CustomUserDetails userDetails, Long userId) {

        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        user.updateApprovalStatus(ApprovalStatus.APPROVED);

        return Message.builder()
                .message("해당 유저의 승인이 완료되었습니다.")
                .build();
    }

    @Transactional
    public Message denyUser(CustomUserDetails userDetails, Long userId) throws AlreadyApprovedUser {

        if (!userDetails.getUsername().equals(MASTER_USERNAME)) {
            throw new IllegalArgumentException("접근 권한이 없습니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException());

        if (user.getApprovalStatus() == ApprovalStatus.APPROVED) {
            throw new AlreadyApprovedUser();
        }

        user.updateApprovalStatus(ApprovalStatus.REJECTED);

        return Message.builder()
                .message("해당 유저의 승인이 거절되었습니다.")
                .build();
    }
}
