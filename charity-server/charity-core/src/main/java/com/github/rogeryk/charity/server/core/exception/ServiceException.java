package com.github.rogeryk.charity.server.core.exception;

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

    public static ServiceException of(int code, String msg) {
        return new ServiceException(code, msg);
    }
}
