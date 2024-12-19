package com.studentvote.global.payload;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_PARAMETER(400,null,"잘못된 요청 데이터입니다."),
    INVALID_REPRESENTATION(400,null,"잘못된 표현 입니다."),
    INVALID_FILE_PATH(400,null,"잘못된 파일 경로 입니다."),
    INVALID_OPTIONAL_ISPRESENT(400,null,"해당 값이 존재하지 않습니다."),
    INVALID_CHECK(400,null,"해당 값이 유효하지 않습니다."),
    INVALID_AUTHENTICATION(400,null,"잘못된 인증입니다."),
    INVALID_ELECTIONCOMMITTEENOTICE(400,null,"선거관리위원회 공고문이 누락되었습니다."),
    INVALID_ELECTIONREGULATIONAMENDNOTICE(400,null,"선거규정 개정공고문이 누락되었습니다."),
    INVALID_CANDIDATERECUITMENTNOTICE(400,null,"후보자 모집공고문이 누락되었습니다."),
    INVALID_ELECTIONREGULATION(400,null,"선거규정이 누락되었습니다."),
    INVALID_CANDIDATE_INFO_IMAGE(400,null,"입후보자 공고 이미지가 누락되었습니다."),
    INVALID_LOGO_IMAGE(400,null,"로고 이미지가 누락 되었습니다"),
    STATUS_NOT_APPROVED(400,null,"후보자로 승인되지 않은 계정입니다");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
