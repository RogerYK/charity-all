package com.github.rogeryk.charity.server.core.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    private String nickName;

    private String avatar;

    private String presentation;

    private boolean followed;

}
