package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.App;
import com.github.rogeryk.charity.utils.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @Test
    public void allTest() {
        Response res = categoryController.all();
        Assert.assertEquals(0, res.getErrCode());
    }

}