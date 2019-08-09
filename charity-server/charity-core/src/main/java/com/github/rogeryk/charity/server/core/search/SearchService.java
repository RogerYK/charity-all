package com.github.rogeryk.charity.server.core.search;

import com.github.rogeryk.charity.server.core.search.index.NewsDocument;
import com.github.rogeryk.charity.server.core.search.index.ProjectDocument;
import com.github.rogeryk.charity.server.core.search.index.UserDocument;
import com.github.rogeryk.charity.server.core.search.repository.NewsDocumentRepository;
import com.github.rogeryk.charity.server.core.search.repository.ProjectDocumentRepository;
import com.github.rogeryk.charity.server.core.search.repository.UserDocumentRepository;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.Project;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.NewsRepository;
import com.github.rogeryk.charity.server.db.repository.ProjectRepository;
import com.github.rogeryk.charity.server.db.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SearchService {
    @Autowired
    private UserDocumentRepository userDocumentRepository;
    @Autowired
    private ProjectDocumentRepository projectDocumentRepository;
    @Autowired
    private NewsDocumentRepository newsDocumentRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UserRepository userRepository;

    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    public PageData<ProjectDocument> searchProject(String text, int page, int size) {
        return PageData.of(projectDocumentRepository.searchByKeyword(text, page, size));
    }

    public PageData<UserDocument> searchUser(String text, int page, int size) {
        return PageData.of(userDocumentRepository.searchByKeyword(text, page, size));
    }

    public PageData<NewsDocument> searchNews(String text, int page, int size) {
        return PageData.of(newsDocumentRepository.searchByKeyword(text, page, size));
    }

    public void importDocument() {
        executorService.submit(() -> {
            importProject();
            importNews();
            importUser();
        });
    }

    private void importProject() {
        int total = 1;
        int pageSize = 10;
        for (int i = 0; i < total; i++) {
            Page<Project> page = projectRepository.
                    findAllByStatusIn(
                            Project.ProjectStatus.userViewStatus,
                            PageRequest.of(i, pageSize)
                    );
            total = page.getTotalPages();
            List<Project> projectList = page.getContent();
            projectList.forEach(project ->
                    projectDocumentRepository.save(ProjectDocument.create(project)));
        }
    }

    private void importNews() {
        int total = 1;
        int pageSize = 10;
        for (int i = 0; i < total; i++) {
            Page<News> page = newsRepository.
                    findAll(
                            PageRequest.of(i, pageSize)
                    );
            total = page.getTotalPages();
            page.getContent()
                    .forEach(news -> newsDocumentRepository.save(NewsDocument.create(news)));
        }
    }

    private void importUser() {
        int total = 1;
        int pageSize = 10;
        for (int i = 0; i < total; i++) {
            Page<User> page =userRepository.
                    findAll(
                            PageRequest.of(i, pageSize)
                    );
            total = page.getTotalPages();
            page.getContent()
                    .forEach(user -> userDocumentRepository.save(UserDocument.create(user)));
        }
    }

}
