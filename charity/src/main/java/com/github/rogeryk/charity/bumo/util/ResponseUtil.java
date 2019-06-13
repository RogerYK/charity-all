package com.github.rogeryk.charity.bumo.util;

import com.github.rogeryk.charity.bumo.BumoException;

import io.bumo.model.response.BaseResponse;

public class ResponseUtil {

    private ResponseUtil() {}

    public static  <T extends BaseResponse> T check(T response) {
        if (response.getErrorCode() != 0) {
            throw new BumoException(response.getErrorCode(), response.getErrorDesc());
        }
        return response;
    }
}
