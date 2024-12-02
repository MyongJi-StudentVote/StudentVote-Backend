package com.studentvote.domain.electionManagementInfo.dto.response;


public record ElectionBasicInfoResponse(
        Long id,
        String electionCommitteeNoticeUrl,
        String electionRegulationAmendmentNoticeUrl,
        String candidateRecruitmentNoticeUrl,
        String electionRegulationUrl
) {
}
