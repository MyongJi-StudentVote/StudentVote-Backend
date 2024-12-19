package com.studentvote.domain.user.domain;

import com.studentvote.domain.candidateInfo.domain.CandidateInfo;
import com.studentvote.domain.common.BaseEntity;
import jakarta.persistence.*;
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

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.username = name;
        this.role = Role.USER;
        this.approvalStatus = ApprovalStatus.PENDING;
    }

    public static User of(String email, String password, String name) {
        return new User(email, password, name);
    }
}