package com.loripin.auto.service;

import com.loripin.auto.model.Article;
import com.loripin.auto.repos.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //   public List<Article> findByTag(String tag){
  //      return articleRepo.findByTag(tag);
  //  }


}
