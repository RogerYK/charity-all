package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.service.CategoryService;
import com.github.rogeryk.charity.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public Response save(Category category) {
        categoryService.save(category);
        return Response.ok(null);
    }

    @GetMapping("/all")
    public Response all() {
        return Response.ok(categoryService.getCategories());
    }
}
