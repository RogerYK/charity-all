package com.github.rogeryk.charity.server.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Column(insertable = false, columnDefinition = "default 0")
    private Integer status;

    @JsonIgnore
    @Convert(converter = GalleryConverter.class)
    private List<String> gallery;

    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String summary;

    @Column(insertable = false,nullable = false,columnDefinition = "DECIMAL(10,2) default 0")
    private BigDecimal raisedMoney;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal targetMoney;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    private Date deletedTime;

    private Date startTime;

    private Date endTime;

    private String transactionHash;

    private Integer transactionNumber = 0;

    private String bumoAddress;

    @JsonIgnore
    private String bumoPrivateKey;

    @Column(insertable = false,columnDefinition = "default 0")
    private Integer donorCount;

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
    @ManyToMany(mappedBy = "followProjects")
    private List<User> followedUsers;

    @Column(insertable = false, columnDefinition = "default 0")
    private Integer watchCount;

    @Override
    public String toString() {
        return "Project:{id:"+id+",name:"+name+",img:"+img
                + "status:"+status+",gallery"+gallery.toString()
                +",content:"+content+",summary:"+summary+",raisedMoney:"+raisedMoney
                +",targetMoney:"+targetMoney+",createdTime:"+createdTime
                +",startTime:"+startTime+",endTime:"+endTime+",transactionHash:"+transactionHash
                +",bumoAddress:"+bumoAddress +",bumoPrivateKey:"+bumoPrivateKey+",updateTime:"+updatedTime
                +",donorCount:"+donorCount+"author:"+author.getId()
                +",category:"+category.getId()+
                ",watchCount:"+watchCount;
    }

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

    public static class ProjectStatus {
        public static final int Creating = 0;  //创建中
        public static final int APPLY = 1; //申请状态，项目不会展现
        public static final int EXAMINATION = 2; //审查后，项目显现，可以捐款
        public static final int SUCCESS = 3; //众筹成功
        public static final int FAIL = 4; //众筹失败
        public static final int DELETE = 5; //已被删除

        public static final List<Integer> userViewStatus = Arrays.asList(EXAMINATION, SUCCESS); //用户可见的状态

    }
}
