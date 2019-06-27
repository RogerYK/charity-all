package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.App;
import com.github.rogeryk.charity.server.web.service.TransactionService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void rechargeTest() {
        transactionService.recharge(58L, BigDecimal.valueOf(100));
    }

    @Test
    public void donate() {
        transactionService.donate(58L, 20L, BigDecimal.valueOf(100));
    }

}
