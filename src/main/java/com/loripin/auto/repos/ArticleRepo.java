package com.loripin.auto.repos;

import com.loripin.auto.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {
    List<Article> findAllByOrderByIdDesc();
    // List<Article> findByTag(String tag);
}
