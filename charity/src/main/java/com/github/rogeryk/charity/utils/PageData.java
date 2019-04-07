package com.github.rogeryk.charity.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    private Long total;

    private List<T> content;

    public static <T> PageData<T> of(Long total, List<T> content) {
        return new PageData<>(total, content);
    }

    public static <T> PageData<T> of(Page<T> page) {
        return new PageData<>(page.getTotalElements(), page.getContent());
    }
}
