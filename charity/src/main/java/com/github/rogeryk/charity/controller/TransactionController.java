package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.controller.form.DonateForm;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.service.TransactionService;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/transaction")
@Slf4j
@Validated
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/donate")
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
