package com.github.rogeryk.charity.server.core.bumo.util;

import com.github.rogeryk.charity.server.core.bumo.BumoException;

import io.bumo.model.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseUtil {

    private ResponseUtil() {}

    public static  <T extends BaseResponse> T check(T response) {
        if (response.getErrorCode() != 0) {
            log.error("bumo exception error code {}, error desc {}", response.getErrorCode(), response.getErrorDesc());
            throw new BumoException(response.getErrorCode(), response.getErrorDesc());
        }
        return response;
    }
}
