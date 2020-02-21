package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CountAnalysis {
    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    private Date createdTime;
    @LastModifiedDate
    private Date updatedTime;
    private Date deletedTime;
    @Column(columnDefinition = "date")
    private Date date;
    private Integer count;
    private CountType countType;

    public enum CountType {
        User,
        Project,
        Transaction;
    }
}
