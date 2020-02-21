package com.github.rogeryk.charity.server.web.admain.controller.form;

import com.github.rogeryk.charity.server.core.util.PageParam;
import lombok.Data;

@Data
public class UserListParams {
    private Long id;
    private String nickName;
    private String phoneNumber;
    private PageParam pageParam;
}
