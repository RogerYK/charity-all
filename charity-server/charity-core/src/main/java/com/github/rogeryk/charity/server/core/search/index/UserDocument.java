package com.github.rogeryk.charity.server.core.search.index;

import com.github.rogeryk.charity.server.db.domain.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;

@Data
@Document(indexName = "user")
public class UserDocument {
    @Id
    private Long id;
    private String nickName;
    @Field(index = false)
    private String avatar;
    private String presentation;

    public static UserDocument create(User user) {
        UserDocument document = new UserDocument();
        document.setId(user.getId());
        document.setNickName(user.getNickName());
        document.setAvatar(user.getAvatar());
        document.setPresentation(user.getPresentation());
        return document;
    }
}
