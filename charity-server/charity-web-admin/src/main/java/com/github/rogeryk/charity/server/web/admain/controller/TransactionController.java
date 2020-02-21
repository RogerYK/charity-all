package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.web.admain.controller.form.TransactionListParams;
import com.github.rogeryk.charity.server.web.admain.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/list")
    public Response list(@RequestBody TransactionListParams params) {
        return Response.ok(transactionService.list(params));
    }

}
