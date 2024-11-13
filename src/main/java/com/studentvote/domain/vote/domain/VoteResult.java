package com.studentvote.domain.vote.domain;

import com.studentvote.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VoteResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String mainCandidateName;

    private String mainCandidateDepartment;

    private String mainCandidateStudentId;

    private String viceCandidateName;

    private String viceCandidateDepartment;

    private String viceCandidateStudentId;

    private Integer totalVoters;

    private Integer totalVotes;

    private Integer approvalVotes;

    private Integer oppositionVotes;
}
