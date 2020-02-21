package com.github.rogeryk.charity.server.db.domain.vo;

import com.github.rogeryk.charity.server.db.domain.CountAnalysis;
import lombok.Value;

import java.text.SimpleDateFormat;

@Value
public class CountData {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String date;
    private Integer count;

    public static CountData from(CountAnalysis countAnalysis) {
        return new CountData(dateFormat.format(countAnalysis.getDate()), countAnalysis.getCount());
    }
}
