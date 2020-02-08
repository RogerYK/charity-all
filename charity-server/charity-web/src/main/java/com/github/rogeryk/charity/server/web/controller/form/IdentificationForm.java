package com.github.rogeryk.charity.server.web.controller.form;

import com.github.rogeryk.charity.server.db.domain.Identification;
import lombok.Data;

@Data
public class IdentificationForm {
    private String identificationType;
    private String identityCardName;
    private String identityCardNumber;
    private String identityCardPicture;
    private String email;
    private String phoneNumber;
    private String userPicture;
    private String sex;
    private String nation;
    private String province;
    private String city;
    private String region;
    private String detailAddress;
    private String company;
    private String introduction;
    private String profession;

    public void merge(Identification identification) {
        identification.setIdentificationType(Identification.IdentificationType.valueOf(identificationType));
        identification.setIdentityCardName(identityCardName);
        identification.setIdentityCardNumber(identityCardNumber);
        identification.setIdentityCardPicture(identityCardPicture);
        identification.setEmail(email);
        identification.setPhoneNumber(phoneNumber);
        identification.setUserPicture(userPicture);
        identification.setSex(Identification.Sex.valueOf(sex));
        identification.setNation(nation);
        identification.setProvince(province);
        identification.setCity(city);
        identification.setRegion(region);
        identification.setDetailAddress(detailAddress);
        identification.setCompany(company);
        identification.setIntroduction(introduction);
        identification.setProfession(profession);
    }
}
