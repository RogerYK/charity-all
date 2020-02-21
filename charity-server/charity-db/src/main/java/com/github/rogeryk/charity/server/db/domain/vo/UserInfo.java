package com.github.rogeryk.charity.server.db.domain.vo;

import com.github.rogeryk.charity.server.db.domain.User;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserInfo {

    private Long id;
    private String nickname;
    private String avatar;
    private String presentation;
    private String profession;
    private String address;
    private String bumoAddress;
    private User.IdentifyStatus identifyStatus;
    private Integer releasedProjectCount = 0;
    private Integer favorUserCount = 0;
    private Integer donatedCount = 0;
    private BigDecimal donatedMoney = BigDecimal.ZERO;

    public static UserInfo from(User user, int donatedCount, int releasedProjectCount) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setAddress(user.getAddress());
        userInfo.setNickname(user.getNickName());
        userInfo.setPresentation(user.getPresentation());
        userInfo.setDonatedCount(donatedCount);
        userInfo.setReleasedProjectCount(releasedProjectCount);
        userInfo.setBumoAddress(user.getBumoAddress());
        userInfo.setIdentifyStatus(user.getIdentifyStatus());
        return userInfo;
    }

}
