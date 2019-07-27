package com.github.rogeryk.charity.server.web.service;

import com.github.rogeryk.charity.server.core.search.index.NewsDocument;
import com.github.rogeryk.charity.server.core.search.repository.NewsDocumentRepository;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.User;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.NewsRepository;
import com.github.rogeryk.charity.server.web.controller.form.NewsForm;
import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private NewsDocumentRepository newsDocumentRepository;

    public News byId(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ServiceException(ErrorCodes.NEWS_NOT_EXIST, "新闻不存在"));
    }

    public List<News> allNews() {
        return newsRepository.findAll();
    }

    public List<News> hotNews(int n) {
        return newsRepository.findHotNews(n);
    }

    public List<News> latestNews(int n) {
        return newsRepository.findLatestNews(n);
    }

    public PageData<News> all(Pageable pageable) {
        return PageData.of(newsRepository.findAll(pageable));
    }

    public PageData<News> byUser(Long userId, Pageable pageable) {
        return PageData.of(newsRepository.findByAuthor_Id(userId, pageable));
    }

    public void save(NewsForm form, Long userId) {
        News news;
        if (form.getId() == null) {
            news = new News();
        } else {
            news = newsRepository.findById(form.getId())
                    .orElseThrow(() -> new ServiceException(ErrorCodes.NEWS_NOT_EXIST, "新闻不存在"));
        }

        User user = new User();
        user.setId(userId);

        news.setTitle(form.getTitle());
        news.setIntroduce(form.getIntroduce());
        news.setContent(form.getContent());
        news.setImg(form.getImg());
        news.setAuthor(user);
        news = newsRepository.saveAndFlush(news);

        NewsDocument document = NewsDocument.create(news);
        newsDocumentRepository.save(document);
    }
}
