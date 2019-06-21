package com.github.rogeryk.charity.controller;


import com.github.rogeryk.charity.App;
import com.github.rogeryk.charity.controller.form.SignForm;
import com.github.rogeryk.charity.utils.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    @Transactional
    public void signTest() {
        SignForm failForm = new SignForm();
        failForm.setPhoneNumber("10000000001");
        failForm.setPassword("123456");
        Response res = userController.sign(failForm);
        Assert.assertEquals(0, res.getErrCode());
    }

    @Test
    public void userInfoTest() {
        Response res = userController.getUserInfo(58L);
        Assert.assertEquals(0, res.getErrCode());
    }
}
