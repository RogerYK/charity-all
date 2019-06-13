package com.github.rogeryk.service;

import com.github.rogeryk.charity.App;
import com.github.rogeryk.charity.service.ProjectService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ProjectServiceTest {

    @Autowired
    private ProjectService service;

    @Test
    public void findProjectVo() {

    }
}
