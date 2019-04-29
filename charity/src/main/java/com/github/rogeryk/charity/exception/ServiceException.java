package com.github.rogeryk.charity.exception;

public class ServiceException extends RuntimeException{

    private final int code;

    private final String msg;

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}