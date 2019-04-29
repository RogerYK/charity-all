package com.github.rogeryk.charity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer", "fieldHandler"})
@EntityListeners(AuditingEntityListener.class)
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String img; //项目封面地址

    @JsonIgnore
    @Convert(converter = GalleryConverter.class)
    private List<String> gallery;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(nullable = false,columnDefinition = "DECIMAL(10,2) default 0")
    private BigDecimal raisedMoney;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal targetMoney;


    @CreatedDate
    private Date createTime = new Date();

    private Date startTime;

    private Date endTime;


    @LastModifiedDate
    private Date updateTime;

    private Integer donorCount = 0;

    @ManyToOne
    private User author;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "project")
    private List<ProjectSchedule> projectSchedules;

    @JsonIgnore
    @ManyToMany
    private List<User> favorUsers;

    public static class GalleryConverter implements AttributeConverter<List<String>, String> {

        @Override
        public String convertToDatabaseColumn(List<String> strings) {
            if (strings == null) return "";

            return strings.stream().reduce((a, b) -> a + ";" + b).orElse("");
        }

        @Override
        public List<String> convertToEntityAttribute(String s) {
            if (s.isEmpty()) return new ArrayList<>();

            return new ArrayList<>(Arrays.asList(s.split(";")));
        }
    }
}
