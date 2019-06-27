package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class RecommendProject {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Project project;
}
