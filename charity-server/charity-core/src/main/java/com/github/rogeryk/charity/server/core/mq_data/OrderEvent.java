package com.github.rogeryk.charity.server.core.mq_data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderEvent {

    public static final int DONATION = 0;
    public static final int RECHARGE = 1;
    public static final int CONSUMER = 2;
    public static final int CHECK = 3;

    private int orderType;

    private Long userId;

    private Long anotherId;

    private BigDecimal amount;

    private Long uniqueId;
}
