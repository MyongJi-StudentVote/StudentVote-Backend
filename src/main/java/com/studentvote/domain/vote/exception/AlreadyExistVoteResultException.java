package com.studentvote.domain.vote.exception;

public class AlreadyExistVoteResultException extends RuntimeException {
    public AlreadyExistVoteResultException() {
        super("이미 등록된 개표 결과 이미지가 존재합니다.");
    }
}
