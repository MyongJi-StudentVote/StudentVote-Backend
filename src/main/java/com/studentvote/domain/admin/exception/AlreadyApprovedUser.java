package com.studentvote.domain.admin.exception;

public class AlreadyApprovedUser extends IllegalArgumentException {
    public AlreadyApprovedUser() {
        super("이미 승인된 유저입니다.");
    }
}
