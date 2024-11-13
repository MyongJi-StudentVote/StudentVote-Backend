package com.studentvote.domain.vote.domain;

import com.studentvote.domain.common.BaseEntity;
import com.studentvote.domain.user.domain.Governance;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String description;

    private Double voteRate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "governance_id")
    private Governance governance;
}
