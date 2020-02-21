package com.github.rogeryk.charity.server.web.admain.controller.form;

import com.github.rogeryk.charity.server.core.util.PageParam;
import lombok.Data;

@Data
public class TransactionListParams {
    private Long id;
    private String hash;
    private PageParam pageParam;
}
