package com.loripin.auto.repos;

import com.loripin.auto.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);
    List<Article> findAllByOrderByIdDesc();



    // List<Article> findByTag(String tag);
}
