package com.github.rogeryk.charity.server.db.domain.vo;

import com.github.rogeryk.charity.server.db.domain.Category;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.ProjectSchedule;
import com.github.rogeryk.charity.server.db.domain.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Value;

@Value
public class ProjectDetailVO {

    private Long id;
    private String name;
    private String img;
    private List<String> gallery;
    private String content;
    private String summary;
    private BigDecimal raisedMoney;
    private BigDecimal targetMoney;
    private Date startTime;
    private Date endTime;
    private String bumoAddress;
    private Integer donorCount;
    private User author;
    private Category category;
    private List<ProjectSchedule> projectSchedules;
    private boolean followed;


    public static ProjectDetailVO valueOf(Project project, boolean followed) {
        return  new ProjectDetailVO(
                project.getId(),
                project.getName(),
                project.getImg(),
                project.getGallery(),
                project.getContent(),
                project.getSummary(),
                project.getRaisedMoney(),
                project.getTargetMoney(),
                project.getStartTime(),
                project.getEndTime(),
                project.getBumoAddress(),
                project.getDonorCount(),
                project.getAuthor(),
                project.getCategory(),
                project.getProjectSchedules(),
                followed
        );
    }
}
