package com.loripin.auto.repos;

import com.loripin.auto.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    List<Reply> findByArticleIdOrderByIdAsc(Long id);

    List<Reply> findBySpotIdOrderByIdAsc(Long id);

    List<Reply> findByModificationIdOrderByIdAsc(Long id);
}
