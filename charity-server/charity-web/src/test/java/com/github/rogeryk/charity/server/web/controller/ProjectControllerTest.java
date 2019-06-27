package com.github.rogeryk.charity.server.web.controller;

import com.github.rogeryk.charity.server.App;
import com.github.rogeryk.charity.server.web.utils.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ProjectControllerTest {

    @Autowired ProjectController projectController;


    @Test()
    public void hotTest() {
        Response response = projectController.getHotProjects(1);
        Assert.assertEquals(0, response.getErrCode());
    }

    @Test()
    public void recommendProject() {
        Response res = projectController.getRecommendProjects(5);
        Assert.assertEquals(0, res.getErrCode());
    }

}
