package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.web.admain.controller.form.IdParams;
import com.github.rogeryk.charity.server.web.admain.controller.form.IdentificationParams;
import com.github.rogeryk.charity.server.web.admain.service.IdentificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/identifications")
public class IdentificationController {

    @Autowired
    private IdentificationService identificationService;

    @PostMapping("/list")
    public Response list(@Validated @RequestBody IdentificationParams params) {
        log.debug("identification list params:{}", params);
        return Response.ok(identificationService.list(params));
    }

    @PostMapping("/pass")
    public Response pass(@Validated @RequestBody IdParams params) {
        identificationService.pass(params.getId());
        return Response.ok();
    }

    @PostMapping("/reject")
    public Response reject(@Validated @RequestBody IdParams params) {
        identificationService.reject(params.getId());
        return Response.ok();
    }
}
