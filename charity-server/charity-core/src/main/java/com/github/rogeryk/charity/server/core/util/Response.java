package com.github.rogeryk.charity.server.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Response {

    private int errCode;

    private Object data;

    private String msg;

    public static  Response ok(Object data) {
        return new Response(0, data, "");
    }

    public static Response ok() {
        return new Response(0, null, null);}

    public static Response error(int errCode, String msg) {
        return new Response(errCode, null, msg);
    }
}
