package com.github.rogeryk.charity.server.core.search.index;

import com.github.rogeryk.charity.server.db.domain.Project;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;

import lombok.Data;

@Data
@Document(indexName = "project")
public class ProjectDocument {
    @Id
    private Long id;
    @Field
    private String name;
    @Field
    private String summary;
    @Field
    private String content;
    @Field(index = false)
    private String img;
    @Field
    private BigDecimal raisedMoney;
    @Field
    private BigDecimal targetMoney;
    @Field
    private Integer donorCount;
    @Field
    private Author author;

    public static ProjectDocument create(Project project) {
        ProjectDocument document = new ProjectDocument();
        document.setId(project.getId());
        document.setName(project.getName());
        document.setSummary(project.getSummary());
        document.setContent(project.getContent());
        document.setImg(project.getImg());
        document.setRaisedMoney(project.getRaisedMoney() == null ? BigDecimal.ZERO : project.getRaisedMoney());
        document.setTargetMoney(project.getTargetMoney());
        document.setDonorCount(project.getDonorCount());

        Author author = new Author();
        author.setId(project.getAuthor().getId());
        author.setNickName(project.getAuthor().getNickName());
        author.setAvatar(project.getAuthor().getAvatar());
        document.setAuthor(author);
        return document;
    }
}
