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
public class VoteResult extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    private String resultImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "governance_id")
    private Governance governance;


    public VoteResult(String resultImage, Governance governance) {
        this.resultImage = resultImage;
        this.governance = governance;
    }

    public static VoteResult of(String resultImage, Governance governance) {
        return new VoteResult(resultImage, governance);
    }
}
