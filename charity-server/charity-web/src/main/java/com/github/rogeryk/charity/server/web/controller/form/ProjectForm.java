package com.github.rogeryk.charity.server.web.controller.form;

import com.github.rogeryk.charity.server.db.domain.Category;
import com.github.rogeryk.charity.server.db.domain.Project;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProjectForm {

    private Long id;

    @NotBlank
    private String img;

    private List<String> gallery;

    @NotNull
    private Long categoryId;

    @NotNull
    private BigDecimal targetMoney;

    private String content;

    private Date startTime;

    private Date endTime;

    public void merge(Project project) {
        project.setId(id);
        project.setImg(img);
        project.setGallery(gallery);

        if (categoryId != null) {
            Category category = new Category();
            category.setId(categoryId);
        }

        project.setTargetMoney(targetMoney);
        project.setContent(content);
        project.setStartTime(startTime);
        project.setEndTime(endTime);
    }

}
