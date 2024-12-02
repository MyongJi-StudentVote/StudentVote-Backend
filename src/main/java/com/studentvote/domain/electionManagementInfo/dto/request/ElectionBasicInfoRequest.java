package com.studentvote.domain.electionManagementInfo.dto.request;


import org.springframework.web.multipart.MultipartFile;

public record ElectionBasicInfoRequest(
        MultipartFile electionCommitteeNotice,
        MultipartFile electionRegulationAmendmentNotice ,
        MultipartFile candidateRecruitmentNotice ,
        MultipartFile electionRegulation
) {
}
