package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.App;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.service.SearchService;
import com.github.rogeryk.charity.utils.PageData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SearchControllerTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void searchProject() {
        PageData<Project> pageData = searchService.searchProject("玉洪", 0, 5);
        System.out.println(pageData.getTotal());
        System.out.println(pageData.getContent().size());
        Assert.assertNotNull(pageData);
    }
}
