package com.github.rogeryk.charity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @JsonIgnore
    @ManyToOne
    private Project project;

    @ManyToOne
    private User commenter;

    @OneToMany(mappedBy = "lastComment")
    private List<Comment> subComment;

    @JsonIgnore
    @ManyToOne
    private Comment lastComment;
}
