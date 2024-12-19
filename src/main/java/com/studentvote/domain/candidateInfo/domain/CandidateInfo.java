package com.studentvote.domain.candidateInfo.domain;

import com.studentvote.domain.common.BaseEntity;
import com.studentvote.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CandidateInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String candidateName;

    private String candidateContactAddress;

    @Column(length = 1000)
    private String candidateInfoImage;

    @Column(length = 1000)
    private String logoImage;

    @Enumerated(EnumType.STRING)
    private ElectionType electionType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
