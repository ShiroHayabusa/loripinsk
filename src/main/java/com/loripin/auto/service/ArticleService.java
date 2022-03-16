package com.loripin.auto.service;

import com.loripin.auto.model.Article;
import com.loripin.auto.repos.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepo articleRepo;

    @Autowired
    public ArticleService(ArticleRepo articleRepo) {
        this.articleRepo = articleRepo;
    }

    public Article findById(Long id) {
        return articleRepo.getOne(id);
    }

    public List<Article> findAll() {
        return articleRepo.findAll();
    }

    public Article saveArticle(Article article) {
        return articleRepo.save(article);
    }

    public void deleteById(Long id) {
        articleRepo.deleteById(id);
    }

    public List<Article> findAllByOrderByIdDesc() {
        return articleRepo.findAllByOrderByIdDesc();
    }

    public Page<Article> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Article> list;

        if (articleRepo.findAll().size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, articleRepo.findAll().size());
            list = articleRepo.findAllByOrderByIdDesc().subList(startItem, toIndex);
        }

        Page<Article> articlePage = new PageImpl<Article>(list, PageRequest.of(currentPage, pageSize), articleRepo.findAll().size());

        return articlePage;
    }
}
