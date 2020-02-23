package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    private Date deletedTime;

    @Column(unique = true, nullable = false)
    private Long uniqueId;

    private String hash;

    private TransactionType type;

    private TransactionStatus status;

    private BigDecimal money;

    @ManyToOne
    private User payer; //付款者

    @ManyToOne
    private User payee; //收款者

    @ManyToOne
    private Project project;

    public static enum TransactionType {
        Donation,
        Consumption,
        Recharge,

    }

    public enum TransactionStatus {
        UnPay,
        Paying,
        Success,
        Failed;
    }
}
