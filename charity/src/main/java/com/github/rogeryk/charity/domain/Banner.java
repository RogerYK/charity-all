package com.github.rogeryk.charity.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Banner {

    @Id
    @GeneratedValue
    private Long id;

    private String img;

    @OneToOne
    private Project project;
}
