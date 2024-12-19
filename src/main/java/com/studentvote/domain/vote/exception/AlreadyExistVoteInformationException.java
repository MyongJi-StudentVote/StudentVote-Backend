package com.studentvote.domain.vote.exception;

public class AlreadyExistVoteInformationException extends RuntimeException {
    public AlreadyExistVoteInformationException() {
        super("이미 선거 정보가 등록되어 있습니다.");
    }
}
