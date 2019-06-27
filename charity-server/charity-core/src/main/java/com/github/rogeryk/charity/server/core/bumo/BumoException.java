package com.github.rogeryk.charity.server.core.bumo;

public class BumoException extends RuntimeException{

    private final int errorCode;

    private final String msg;

    public BumoException(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "bumo exception:(errorCode="+errorCode+",msg="+msg+")\n"
                + super.toString();
    }
}
