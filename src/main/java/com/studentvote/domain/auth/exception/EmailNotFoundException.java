package com.studentvote.domain.auth.exception;

public class EmailNotFoundException extends IllegalArgumentException {
    public EmailNotFoundException() {
        super("가입되지 않은 이메일입니다.");
    }
}
