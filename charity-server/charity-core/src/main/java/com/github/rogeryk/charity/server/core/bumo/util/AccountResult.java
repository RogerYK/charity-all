package com.github.rogeryk.charity.server.core.bumo.util;

import lombok.Data;

@Data
public class AccountResult {
    private String address;
    private String privateKey;
    private String publicKey;
    private String hash;
}
