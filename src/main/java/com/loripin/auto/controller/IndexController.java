package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.model.dto.SpotDto;
import com.loripin.auto.repos.ArticleRepo;
import com.loripin.auto.repos.FileStorage;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class IndexController {

    public static final String DATE_FORMAT_NOW = "dd.MM.yyyy HH:mm";

    private final
    SpotService spotService;

    private final
    ReplyService replyService;
    private final
    UserService userService;
    private final
    CommentService commentService;

    private final
    CarmodelService carmodelService;
    private final
    ManufacturerService manufacturerService;

    private final
    ModificationService modificationService;

    private final RestyleService restyleService;

    private final FileStorage fileStorage;

    private final ArticleRepo articleRepo;

    private final ArticleService articleService;

    @Autowired
    public IndexController(ArticleService articleService,
                           ArticleRepo articleRepo,
                           FileStorage fileStorage,
                           RestyleService restyleService,
                           ModificationService modificationService,
                           ManufacturerService manufacturerService,
                           CarmodelService carmodelService,
                           CommentService commentService,
                           UserService userService,
                           ReplyService replyService,
                           SpotService spotService) {
        this.articleService = articleService;
        this.articleRepo = articleRepo;
        this.fileStorage = fileStorage;
        this.restyleService = restyleService;
        this.modificationService = modificationService;
        this.manufacturerService = manufacturerService;
        this.carmodelService = carmodelService;
        this.commentService = commentService;
        this.userService = userService;
        this.replyService = replyService;
        this.spotService = spotService;
    }

    public static Long tempId;

    @Value("${upload.path}")
    private String uploadPath;

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Article> articles = articleService.findAllByOrderByIdDesc();
        model.addAttribute("articles", articles);
        return "index";
    }

    @GetMapping("/articleCreate/{id}")
    public String createArticleForm(Article article, @PathVariable("id") Long id, Model model) {
        Modification modification = modificationService.findById(id);
        model.addAttribute("modification", modification);
        tempId = id;
        return "articleCreate";
    }

    @PostMapping("/articleCreate")
    public String createArticle(Article article, Model model) {
        article.setModification(modificationService.findById(tempId));
        article.setDate(now());
        articleService.saveArticle(article);
        return "redirect:/";
    }

    @GetMapping("articleDelete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("articleUpdate/{id}")
    public String updateArticleForm(@AuthenticationPrincipal User user,
                                    @PathVariable("id") Long id, Model model) {
        List<Modification> modifications = modificationService.findAllByOrderByIdAsc();
        model.addAttribute("modifications", modifications);
        Article article = articleService.findById(id);
        model.addAttribute("article", article);
        user.setTmp(id);
        userService.save(user);

        return "articleUpdate";
    }

    @PostMapping("/articleUpdate")
    public String updateArticle(@AuthenticationPrincipal User user,
                                Article article) {
        articleService.saveArticle(article);

        User user1 = userService.findById(user.getId());

        return "redirect:/articleOpen/" + user1.getTmp();
    }

    @GetMapping("/articleOpen/{id}")
    public String openArticleForm(@AuthenticationPrincipal User user,
                                  @PathVariable Long id,
                                  Comment comment,
                                  Model model) {

        Article article = articleService.findById(id);
        model.addAttribute("article", article);

        List<Comment> comments = commentService.findByArticleIdOrderByIdAsc(id);
        model.addAttribute("comments", comments);

        List<Reply> replies = replyService.findByArticleIdOrderByIdAsc(id);
        model.addAttribute("replies", replies);

        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }
        return "articleOpen";
    }

    @PostMapping("/articleOpen")
    public String openArticle(@AuthenticationPrincipal User user,
                              Comment comment) {

        User user1 = userService.findById(user.getId());

        comment.setArticle(articleService.findById(user1.getTmp()));
        comment.setUser(user);
        comment.setDate(now());

        if (user1.getRating() == null) {
            user1.setRating(1);
        } else {
            user1.setRating(user1.getRating() + 1);
        }

        userService.save(user1);
        commentService.save(comment);
        return "redirect:/articleOpen/" + user1.getTmp();
    }

    @GetMapping("/yandex_85d521b1af0eda05.html")
    public String yandex() {
        return "yandex_85d521b1af0eda05";
    }

    @GetMapping("/google7c5e097f00a57783.html")
    public String google() {
        return "google7c5e097f00a57783";
    }

    @GetMapping("/ajaxExample")
    public String ajaxExample(@AuthenticationPrincipal User user,
                              Model model) {
        List<SpotDto> spots = spotService.findAllByOrderByIdDesc(user);
        model.addAttribute("spots", spots);
        return "ajaxExample";
    }

    @GetMapping("/spot/{spot}/like")
    public String like(@AuthenticationPrincipal User currentUser,
                       @PathVariable Spot spot,
                       RedirectAttributes redirectAttributes,
                       @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = spot.getLikes();

        if (likes.contains(userService.findById(currentUser.getId()))) {
            likes.remove(userService.findById(currentUser.getId()));
        } else {
            likes.add(currentUser);
        }
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));
        return "redirect:" + components.getPath();
    }

    @GetMapping("/test")
    public String test(@RequestParam Manufacturer manufacturer) {

        return "test";
    }
}
