package com.github.rogeryk.charity.server.core.search;

import com.github.rogeryk.charity.server.core.search.index.NewsDocument;
import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;
import com.github.rogeryk.charity.server.core.search.index.UserDocument;
import com.github.rogeryk.charity.server.core.search.repository.NewsDocumentRepository;
import com.github.rogeryk.charity.server.core.search.repository.ProjectDocumentRepository;
import com.github.rogeryk.charity.server.core.search.repository.UserDocumentRepository;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private UserDocumentRepository userDocumentRepository;
    @Autowired
    private ProjectDocumentRepository projectDocumentRepository;
    @Autowired
    private NewsDocumentRepository newsDocumentRepository;


    public PageData<ProjectDocument> searchProject(String text, int page, int size) {
        return PageData.of(projectDocumentRepository.searchByKeyword(text, page, size));
    }

    public PageData<UserDocument> searchUser(String text, int page, int size) {
        return PageData.of(userDocumentRepository.searchByKeyword(text, page, size));
    }

    public PageData<NewsDocument> searchNews(String text, int page, int size) {
        return PageData.of(newsDocumentRepository.searchByKeyword(text, page, size));
    }


}
