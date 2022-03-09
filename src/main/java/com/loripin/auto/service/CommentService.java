package com.loripin.auto.service;

import com.loripin.auto.model.Comment;
import com.loripin.auto.repos.CommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final
    CommentRepo commentRepo;

    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }

    public List<Comment> findAllByOrderByIdDesc() {
        return commentRepo.findAllByOrderByIdDesc();
    }

    public List<Comment> findByArticleIdOrderByIdDesc(Long id) {
        return commentRepo.findByArticleIdOrderByIdDesc(id);
    }

    public List<Comment> findByArticleIdOrderByIdAsc(Long id) {
        return commentRepo.findByArticleIdOrderByIdAsc(id);
    }

    public Comment findById(Long id) {
        return commentRepo.getOne(id);
    }

    public List<Comment> findBySpotIdOrderByIdAsc(Long id) {
        return commentRepo.findBySpotIdOrderByIdAsc(id);
    }

    public List<Comment> findByModificationIdOrderByIdAsc(Long id) {
        return commentRepo.findByModificationIdOrderByIdAsc(id);
    }
}
