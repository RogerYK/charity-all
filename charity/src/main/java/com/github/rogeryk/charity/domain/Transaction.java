package com.github.rogeryk.charity.domain;

import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String hash;

    private TransactionType type;


    @ManyToOne
    private Project project;

    private BigDecimal money;

    @CreatedDate
    private Date createdTime;

    @ManyToOne
    private User payer; //付款者

    @ManyToOne
    private User payee; //收款者

    public static enum TransactionType {
        Donation,
        Consumption,
        Recharge,

    }
}
