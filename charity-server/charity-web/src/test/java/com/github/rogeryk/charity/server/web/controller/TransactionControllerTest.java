package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.App;
import com.github.rogeryk.charity.server.web.controller.form.PageParam;
import com.github.rogeryk.charity.server.web.utils.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TransactionControllerTest {

    private TransactionController transactionController;

    @Test
    public void queryDonationTest() {
        Response response = transactionController.donationRecords(58L, new PageParam());
        Assert.assertEquals(0, response.getErrCode());
    }
}
