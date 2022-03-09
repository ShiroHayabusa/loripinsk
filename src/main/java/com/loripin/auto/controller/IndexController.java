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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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

    @Value("${upload.path}")
    private String uploadPath;

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    @GetMapping("/")
    public String findAll(
            @AuthenticationPrincipal User user,
            HttpServletRequest request,
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            Restyle restyle
    ) {
        int page = 0;
        int size = 10;

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        //model.addAttribute("articles", articleRepo.findAll(PageRequest.of(page, size)));
        List<Article> articles = articleService.findAllByOrderByIdDesc();

        if (articles.size() > 1) {
            Article lastArticle = articles.get(0);
            Article penultArticle = articles.get(1);
            model.addAttribute("lastArticle", lastArticle);
            model.addAttribute("penultArticle", penultArticle);
            articles.removeAll(articles.subList(0, 2));
        }

        model.addAttribute("articles", articles);

        List<SpotDto> spots = spotService.findAllByOrderByIdDesc(user);
        model.addAttribute("spots", spots);

        List<Modification> modifications = modificationService.findAllByOrderByIdDesc();
        model.addAttribute("modifications", modifications);
        int photoCount = 0;

        for (Modification modification: modifications
        ) {
            photoCount = photoCount + modification.getPhotos().size();
        }
        model.addAttribute("photoCount", photoCount);

        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("carmodels", carmodelService.findAll());


        return "index";
    }

    @GetMapping("/articleCreate")
    public String createArticleForm(Article article, Model model){
        List<Modification> modifications = modificationService.findAllByOrderByIdAsc();
        model.addAttribute("modifications", modifications);
        return "articleCreate";
    }

    @PostMapping("/articleCreate")
    public String createArticle(
            @AuthenticationPrincipal User user,
            @Valid Article article,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        article.setAuthor(user);
        article.setDate(now());

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            return "articleCreate";
        } else {

            model.addAttribute("article", null);

            articleService.saveArticle(article);
        }
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
        model.addAttribute("replies",replies);
        user.setTmp(id);
        userService.save(user);
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
    public String test(@RequestParam Manufacturer manufacturer){

        return "test";
    }
}
