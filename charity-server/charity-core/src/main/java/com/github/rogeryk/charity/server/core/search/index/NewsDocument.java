package com.github.rogeryk.charity.server.core.search.index;

import com.github.rogeryk.charity.server.db.domain.News;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.Data;

@Data
@Document(indexName = "news")
public class NewsDocument {
    @Id
    private Long id;
    @Field
    private String title;
    @Field
    private String content;
    @Field
    private String introduce;
    @Field(index = false)
    private String img;
    @Field
    private Author author;

    public static NewsDocument create(News news) {
        NewsDocument document = new NewsDocument();
        document.setId(news.getId());
        document.setTitle(news.getTitle());
        document.setContent(news.getContent());
        document.setIntroduce(news.getIntroduce());
        document.setImg(news.getImg());

        Author author = new Author();
        author.setId(news.getAuthor().getId());
        author.setNickName(news.getAuthor().getNickName());
        author.setAvatar(news.getAuthor().getAvatar());
        document.setAuthor(author);
        return document;
    }
}
