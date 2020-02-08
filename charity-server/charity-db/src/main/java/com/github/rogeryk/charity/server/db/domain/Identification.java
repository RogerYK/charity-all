package com.github.rogeryk.charity.server.db.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Identification  {

    @Id
    @GeneratedValue
    private Long Id;

    @CreatedDate
    private Date createdTime;

    @LastModifiedDate
    private Date updatedTime;

    private Date deletedTime;

    private IdentificationType identificationType;

    private IdentificationState identificationState;

    private String identityCardName;

    private String identityCardNumber;

    private String identityCardPicture;

    private String email;

    private String phoneNumber;

    private String userPicture;

    private Sex sex;

    private String nation;

    private String province;

    private String city;

    private String region;

    private String detailAddress;

    private String profession;

    private String company;

    private String introduction;

    private Long userId;

    public static enum IdentificationType {
        Personal,
        Organization;
    }

    public static enum IdentificationState {
        Apply,
        Pass,
        Reject;
    }

    public static enum Sex {
        Man,
        Woman;
    }
}
