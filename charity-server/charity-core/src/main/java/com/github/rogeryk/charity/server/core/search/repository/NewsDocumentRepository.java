package com.github.rogeryk.charity.server.core.search.repository;

import com.github.rogeryk.charity.server.core.search.index.NewsDocument;
import com.github.rogeryk.charity.server.db.domain.News;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsDocumentRepository extends ElasticsearchRepository<NewsDocument, Long> {

    default Page<NewsDocument> searchByKeyword(String text, int page, int size) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.multiMatchQuery(text,
                "name", "content", "introduce"));
        builder.withPageable(PageRequest.of(page, size));
        return this.search(builder.build());
    }
}
