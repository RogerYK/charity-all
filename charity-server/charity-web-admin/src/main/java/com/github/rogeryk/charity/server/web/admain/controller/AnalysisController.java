package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.web.admain.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/admin/analysis")
public class AnalysisController {

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/user/count/sum")
    public Response sumUserCount() {
        Map<String, Object> data = new TreeMap<>();
        data.put("total", analysisService.sumUserCount());
        return Response.ok(data);
    }

    @GetMapping("/user/count")
    public Response userCountByRange(@NotEmpty String startTime, @NotEmpty String endTime) throws ParseException {
        Map<String, Object> data = new TreeMap<>();
        data.put("items", analysisService.userCountByRange(format.parse(startTime), format.parse(endTime)));
        return Response.ok(data);
    }

    @GetMapping("/project/count/sum")
    public Response sumProjectCount() {
        Map<String, Object> data = new TreeMap<>();
        data.put("total", analysisService.sumProjectCount());
        return Response.ok(data);
    }

    @GetMapping("/project/count")
    public Response projectCountByRange(@NotEmpty String startTime, @NotEmpty String endTime) throws ParseException {
        Map<String, Object> data = new TreeMap<>();
        data.put("items", analysisService.projectCountByRange(format.parse(startTime), format.parse(endTime)));
        return Response.ok(data);
    }

    @GetMapping("/transaction/count/sum")
    public Response sumTransactionCount() {
        Map<String, Object> data = new TreeMap<>();
        data.put("total", analysisService.sumTransactionCount());
        return Response.ok(data);
    }

    @GetMapping("/transaction/count")
    public Response transactionByRange(@NotEmpty String startTime, @NotEmpty String endTime) throws ParseException {
        Map<String, Object> data = new TreeMap<>();
        data.put("items", analysisService.transactionCountByRange(format.parse(startTime), format.parse(endTime)));
        return Response.ok(data);
    }
}


