package com.loripin.auto.service;

import com.loripin.auto.model.Reply;
import com.loripin.auto.repos.ReplyRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService {
    private final
    ReplyRepo replyRepo;

    public ReplyService(ReplyRepo replyRepo) {
        this.replyRepo = replyRepo;
    }

    public Reply save(Reply reply) {
        return replyRepo.save(reply);
    }

    public List<Reply> findByArticleIdOrderByIdAsc(Long id) {
        return replyRepo.findByArticleIdOrderByIdAsc(id);
    }

    public List<Reply> findBySpotIdOrderByIdAsc(Long id) {
        return replyRepo.findBySpotIdOrderByIdAsc(id);
    }

    public List<Reply> findByModificationIdOrderByIdAsc(Long id) {
        return replyRepo.findByModificationIdOrderByIdAsc(id);
    }
}
