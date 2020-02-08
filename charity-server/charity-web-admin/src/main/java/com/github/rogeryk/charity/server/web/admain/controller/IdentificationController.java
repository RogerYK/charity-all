package com.github.rogeryk.charity.server.web.admain.controller;

import com.github.rogeryk.charity.server.core.util.Response;
import com.github.rogeryk.charity.server.db.domain.Identification;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.web.admain.controller.form.IdentificationParams;
import com.github.rogeryk.charity.server.web.admain.service.IdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/admin/identifications")
public class IdentificationController {

    @Autowired
    private IdentificationService identificationService;

    @PostMapping("/list")
    public Response list(IdentificationParams params) {
        return Response.ok(identificationService.list(params));
    }

    @PostMapping("/{id}/pass")
    public Response pass(@PathVariable("id") Long id) {
        identificationService.pass(id);
        return Response.ok();
    }


    @PostMapping("/{id}/reject")
    public Response reject(@PathVariable("id") Long id) {
        identificationService.reject(id);
        return Response.ok();
    }
}
