package com.github.rogeryk.charity.controller;

import com.github.rogeryk.charity.controller.form.SignForm;
import com.github.rogeryk.charity.controller.form.UserForm;
import com.github.rogeryk.charity.domain.Project;
import com.github.rogeryk.charity.domain.Transaction;
import com.github.rogeryk.charity.domain.User;
import com.github.rogeryk.charity.domain.form.UserInfo;
import com.github.rogeryk.charity.exception.ServiceException;
import com.github.rogeryk.charity.service.ProjectService;
import com.github.rogeryk.charity.service.UserService;
import com.github.rogeryk.charity.utils.ErrorCodes;
import com.github.rogeryk.charity.utils.PageData;
import com.github.rogeryk.charity.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
@Slf4j
@Validated
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/sign")
    public Response sign(@RequestBody @Validated SignForm form) {
        User user = userService.findUserByPhoneNumber(form.getPhoneNumber());
        if (user != null) {
            throw new ServiceException(ErrorCodes.USER_EXIST, "用户已存在");
        }
        userService.registerUser(form.getPhoneNumber(), form.getPassword());
        return Response.ok("注册成功");
    }

    @GetMapping("/")
    public Response getUserInfo(Long id) {
        if (id == null) {
            return getCurrentUserInfo();
        }
        UserInfo info;
        info = userService.getUserInfo(id);
        return Response.ok(info);
    }

    private Response getCurrentUserInfo() {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();
        UserInfo info = userService.getUserInfo(phoneNumber);
        return Response.ok(info);
    }

    @PutMapping("/update")
    public Response updateUserInfo(HttpServletRequest request, @RequestBody UserForm form) {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);
        form.merge(user);

        userService.saveUser(user);
        return Response.ok(null);
    }

    @GetMapping("/project/favor")
    public Response getFavorProjects(@RequestParam(defaultValue = "0") Integer page,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     @RequestParam(defaultValue = "ASC") String direction,
                                     @RequestParam(defaultValue = "createTime") String field ) {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction), field));
        Page<Project> projectPage = projectService.findUserFavorProjects(user, pageable);

        return Response.ok(PageData.of(projectPage));
    }

    @GetMapping("/project/release")
    public Response getReleasedProjects(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "9") Integer size,
                                        @RequestParam(defaultValue = "ASC") String direction,
                                        @RequestParam(defaultValue = "createTime") String field) {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction), field));

        Page<Project> projectPage = projectService.findUserReleaseProjects(user, pageable);


        return Response.ok(PageData.of(projectPage));
    }

    @GetMapping("/record/donation")
    public Response getDonationRecords(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(defaultValue = "ASC") String direction,
                                       @RequestParam(defaultValue = "createTime") String field) {
        String phoneNumber = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userService.findUserByPhoneNumber(phoneNumber);

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.valueOf(direction), field));

        Page<Transaction> transactionPage = userService.getDonationRecord(user, pageable);

        return Response.ok(PageData.of(transactionPage));
    }

}
