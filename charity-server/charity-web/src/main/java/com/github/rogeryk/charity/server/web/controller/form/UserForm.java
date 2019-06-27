package com.github.rogeryk.charity.server.web.controller.form;

import com.github.rogeryk.charity.server.db.domain.User;

import java.util.Date;

import lombok.Data;

@Data
public class UserForm {

    private String avatar;

    private String nickName;

    private Integer sex;

    private Date birthday;

    private String address;

    private String presentation;

    public void merge(User user) {
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        if (nickName != null) {
            user.setNickName(nickName);
        }
        if (sex != null) {
            user.setSex(sex);
        }
        if (birthday != null) {
            user.setBirthday(birthday);
        }
        if (address != null) {
            user.setAddress(address);
        }
        if (presentation != null) {
            user.setPresentation(presentation);
        }
    }

}
