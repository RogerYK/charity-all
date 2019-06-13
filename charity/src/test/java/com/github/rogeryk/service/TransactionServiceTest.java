package com.github.rogeryk.service;

import com.github.rogeryk.charity.App;
import com.github.rogeryk.charity.service.TransactionService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TransactionServiceTest {

    private static long testUserId = 58;

    @Autowired
    private TransactionService transactionService;

    @Test
    public void rechargeTest() {
        transactionService.recharge(testUserId, 10L);
    }
}
