package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class AdminUser {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

}
