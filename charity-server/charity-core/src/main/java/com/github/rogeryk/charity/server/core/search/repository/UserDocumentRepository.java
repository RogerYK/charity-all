package com.github.rogeryk.charity.server.core.search.repository;

import com.github.rogeryk.charity.server.core.search.index.UserDocument;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, Long> {

    default Page<UserDocument> searchByKeyword(String text, int page, int size) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("nickName", text));
        builder.withPageable(PageRequest.of(page, size));
        return this.search(builder.build());
    }
}
