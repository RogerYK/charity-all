package com.github.rogeryk.charity.server.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Project> projects;
}
