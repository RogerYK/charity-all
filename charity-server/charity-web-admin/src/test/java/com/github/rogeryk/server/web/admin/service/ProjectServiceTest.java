package com.github.rogeryk.server.web.admin.service;

import com.github.rogeryk.charity.server.AdminApp;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.admain.service.ProjectService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {AdminApp.class})
@RunWith(SpringRunner.class)
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Test
    public void projectTest() {
        PageData<Project> projectPageData = projectService.list(null,
                null,
                null,
                new PageParam());

        System.out.println(projectPageData.getTotal());
    }
}
