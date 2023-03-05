package com.kiwes.backend.member.exception;

import com.kiwes.backend.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    ALREADY_EXIST_USERNAME(600, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    WRONG_PASSWORD(601, HttpStatus.BAD_REQUEST, "비밀번호가 잘못되었습니다."),
    NOT_FOUND_MEMBER(602, HttpStatus.NOT_FOUND, "회원 정보가 없습니다."),
    TOKEN_INVALID(401, HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
