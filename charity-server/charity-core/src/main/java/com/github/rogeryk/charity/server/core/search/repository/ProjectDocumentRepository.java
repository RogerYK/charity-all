package com.github.rogeryk.charity.server.core.search.repository;

import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDocumentRepository extends ElasticsearchRepository<ProjectDocument, Long> {

    default Page<ProjectDocument> searchByKeyword(String text, int page, int size) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.multiMatchQuery(text,
                "name", "summary", "content"));
        builder.withPageable(PageRequest.of(page, size));
        return this.search(builder.build());
    }
}
