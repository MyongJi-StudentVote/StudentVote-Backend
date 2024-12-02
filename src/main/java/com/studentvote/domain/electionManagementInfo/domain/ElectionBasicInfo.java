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

    private String electionCommitteeNotice;

    private String electionRegulationAmendmentNotice;

    private String candidateRecruitmentNotice;

    private String electionRegulation;

}
