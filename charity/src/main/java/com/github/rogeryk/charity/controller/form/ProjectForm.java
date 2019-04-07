package com.github.rogeryk.charity.controller.form;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.Project;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
