package com.github.rogeryk.charity.server.db.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Banner {

    @Id
    @GeneratedValue
    private Long id;

    private String img;

    Long projectId;
}
