package com.studentvote.domain.auth.exception;

public class AlreadyExistIdException extends RuntimeException {
    public AlreadyExistIdException() {
        super("이미 가입된 유저입니다.");
    }
}
