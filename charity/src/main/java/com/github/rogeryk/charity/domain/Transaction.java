package com.github.rogeryk.charity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String tradeNo;

    private TransactionType type;

    @ManyToOne
    private Project project;

    private BigDecimal money;

    @CreatedDate
    private Date createTime;

    @JsonIgnore
    @OneToOne
    private Transaction lastTransaction;

    @ManyToOne
    private User payer; //付款者

    @ManyToOne
    private User payee; //收款者

    public static enum TransactionType {
        Donation,
        Consumption
    }
}
