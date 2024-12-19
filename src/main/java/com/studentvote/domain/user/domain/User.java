package com.studentvote.domain.user.domain;

import com.studentvote.domain.candidateInfo.domain.CandidateInfo;
import com.studentvote.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String email;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private CandidateInfo candidateInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "governance_id")
    private Governance governance;


    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.username = name;
        this.role = Role.USER;
        this.approvalStatus = ApprovalStatus.PENDING;
    }

    public void updateApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public static User of(String email, String password, String name) {
        return new User(email, password, name);
    }
}