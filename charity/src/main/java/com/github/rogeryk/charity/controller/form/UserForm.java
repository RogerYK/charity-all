package com.github.rogeryk.charity.controller.form;

import com.github.rogeryk.charity.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;

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
