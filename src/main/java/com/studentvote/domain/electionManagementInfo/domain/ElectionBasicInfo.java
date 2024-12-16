package com.studentvote.domain.electionManagementInfo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ElectionBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(length = 1000)
    private String electionCommitteeNotice;

    @Column(length = 1000)
    private String electionRegulationAmendmentNotice;

    @Column(length = 1000)
    private String candidateRecruitmentNotice;

    @Column(length = 1000)
    private String electionRegulation;

}
