package com.github.rogeryk.charity.domain.vo;

import com.github.rogeryk.charity.domain.Category;
import com.github.rogeryk.charity.domain.User;

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
    private boolean followed;

    public static ProjectDetailVO valueOf(ProjectVO vo, boolean followed) {
        return  new ProjectDetailVO(
                vo.getId(),
                vo.getName(),
                vo.getImg(),
                vo.getGallery(),
                vo.getContent(),
                vo.getSummary(),
                vo.getRaisedMoney(),
                vo.getTargetMoney(),
                vo.getStartTime(),
                vo.getEndTime(),
                vo.getBumoAddress(),
                vo.getDonorCount(),
                vo.getAuthor(),
                vo.getCategory(),
                followed
        );
    }
}
