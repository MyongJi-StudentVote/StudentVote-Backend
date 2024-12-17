package com.studentvote.domain.auth.exception;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
