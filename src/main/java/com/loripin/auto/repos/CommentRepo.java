package com.loripin.auto.repos;

import com.loripin.auto.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findAllByOrderByIdDesc();

    List<Comment> findByArticleIdOrderByIdDesc(Long id);

    List<Comment> findByArticleIdOrderByIdAsc(Long id);

    List<Comment> findBySpotIdOrderByIdAsc(Long id);

    List<Comment> findByModificationIdOrderByIdAsc(Long id);
}
