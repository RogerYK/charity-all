package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.controller.form.DonateForm;
import com.github.rogeryk.charity.controller.form.PageParam;
import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.TransactionService;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.PageData;
import com.github.rogeryk.charity.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/transaction")
@Slf4j
@Validated
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/donation")
    public Response donationRecords(@NotNull Long userId, PageParam pageParam) {
        User user = userService.findById(userId);
        Page<Transaction> transactionPage = transactionService.donationBy(user, pageParam.toPageable());
        return Response.ok(PageData.of(transactionPage));
    }

    @PostMapping("/donation")
    public Response donate(@Valid @RequestBody DonateForm form) {
        log.debug(form.toString());
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);

        transactionService.donate(user, form.getProjectId(), form.getAmount());

        return Response.ok(null);
    }
}
