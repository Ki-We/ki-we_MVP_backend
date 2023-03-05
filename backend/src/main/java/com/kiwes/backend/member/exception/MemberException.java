package com.kiwes.backend.member.exception;

import com.kiwes.backend.global.exception.BaseException;
import com.kiwes.backend.global.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private final BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }


    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
