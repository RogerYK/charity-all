package com.github.rogeryk.charity.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class News {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String img;

}
