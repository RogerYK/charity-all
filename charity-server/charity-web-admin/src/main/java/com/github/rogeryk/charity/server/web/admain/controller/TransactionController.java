package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.Transaction;
import com.github.rogeryk.charity.server.web.admain.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.bumo.model.response.TransactionSubmitResponse;

@RestController
@RequestMapping("/api/admin/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/list")
    public Response list(Long transactionId, PageParam pageParam) {
        return Response.ok(transactionService.list(transactionId, pageParam));
    }

}
