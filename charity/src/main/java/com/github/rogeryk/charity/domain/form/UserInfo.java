package com.github.rogeryk.charity.domain.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfo {

    private String nickname;

    private String avatar;

    private String presentation;

    private String profession;

    private String address;

    private Integer releasedProjectCount = 0;

    private Integer favorUserCount = 0;

    private Integer donatedCount = 0;

    private BigDecimal donatedMoney = BigDecimal.ZERO;
}
