package com.github.rogeryk.charity.server.web.admain.service;

import com.github.rogeryk.charity.server.core.exception.ServiceException;
import com.github.rogeryk.charity.server.core.util.ErrorCodes;
import com.github.rogeryk.charity.server.core.util.PageParam;
import com.github.rogeryk.charity.server.db.domain.News;
import com.github.rogeryk.charity.server.db.domain.vo.PageData;
import com.github.rogeryk.charity.server.db.repository.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public PageData<News> list(Long newsId, PageParam pageParam) {
        News news = new News();
        news.setCreatedTime(null);
        if (newsId != null) {
            news.setId(newsId);
        }
        Example<News> example = Example.of(news);
        return PageData.of(newsRepository.findAll(example, pageParam.toPageable()));
    }

    public News byId(Long id) {
        return newsRepository.findById(id).orElse(null);
    }


}
