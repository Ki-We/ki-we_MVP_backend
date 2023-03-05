package com.kiwes.backend.jwt.exception;

public class JwtException extends RuntimeException{

    private final String message;

    public JwtException(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
