package com.loripin.auto.controller;

import com.loripin.auto.model.Comment;
import com.loripin.auto.model.Reply;
import com.loripin.auto.model.User;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.loripin.auto.controller.IndexController.now;

@Controller
public class CommentController {
    private final
    ModificationService modificationService;
    private final
    SpotService spotService;
    private final
    UserService userService;
    private final
    ReplyService replyService;
    private final
    CommentService commentService;

    public CommentController(CommentService commentService,
                             ReplyService replyService,
                             UserService userService, SpotService spotService, ModificationService modificationService) {
        this.commentService = commentService;
        this.replyService = replyService;
        this.userService = userService;
        this.spotService = spotService;
        this.modificationService = modificationService;
    }

    @GetMapping("replyCreate/{id}")
    public String createReplyForm(@AuthenticationPrincipal User user,
                                  @PathVariable Long id,
                                  Reply reply,
                                  Model model) {

        Comment comment = commentService.findById(id);
        model.addAttribute("comment", comment);

        user.setTmpComment(id);
        if (comment.getModification() != null) {
            user.setTmpModification(comment.getModification().getId());
        } else {
            user.setTmpSpot(comment.getSpot().getId());
        }
        userService.save(user);

        return "replyCreate";
    }

    @PostMapping("replyCreate")
    public String createReply(@AuthenticationPrincipal User user,
                              Reply reply) {

        User user1 = userService.findById(user.getId());

        if (user1.getTmpModification() != null) {
            reply.setModification(modificationService.findById(user1.getTmpModification()));
        } else {
            reply.setSpot(spotService.findById(user1.getTmpSpot()));
        }

        reply.setUser(user1);
        reply.setComment(commentService.findById(user1.getTmpComment()));
        reply.setDate(now());

        if (user1.getRating() == null) {
            user.setRating(1);
        } else {
            user.setRating(user1.getRating() + 1);
        }

        replyService.save(reply);
        if (user1.getTmpModification() != null) {
            return "redirect:/catalog/manufacturer/carmodel/generation/restyle/modification/" + user1.getTmpModification();
        } else {
            return "redirect:/spotOpen/" + user1.getTmpSpot();
        }

    }
}
