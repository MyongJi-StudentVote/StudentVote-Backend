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
import jakarta.persistence.ManyToOne;
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

    private Integer voteCount;

    private Double voteRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "governance_id")
    private Governance governance;

    public Vote(Integer voteCount, Double voteRate, Governance governance) {
        this.voteCount = voteCount;
        this.voteRate = voteRate;
        this.governance = governance;
    }

    public static Vote of(Integer voteCount, Double voteRate, Governance governance) {
        return new Vote(voteCount, voteRate, governance);
    }
}
