package com.github.rogeryk.charity.server.web.controller.form;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PageParam {

    private int page = 0;

    private int size = 12;

    private String direction = "desc";

    private String field = "createdTime";

    public Pageable toPageable() {
       return PageRequest.of(page, size,
                Sort.by(Sort.Direction.fromString(direction), field));
    }
}
