package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.model.dto.SpotDto;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.loripin.auto.service.FileUpload.resultFilename;

@Controller
public class SpotController {
    private final
    ManufacturerService manufacturerService;
    private final
    CarmodelService carmodelService;
    private final
    ReplyService replyService;
    private final
    CommentService commentService;
    private final
    UserService userService;
    private final
    ModificationService modificationService;
    private final
    FileUpload fileUpload;
    private final
    CountryService countryService;
    private final
    SpotService spotService;

    public SpotController(SpotService spotService,
                          CountryService countryService,
                          FileUpload fileUpload,
                          ModificationService modificationService,
                          UserService userService,
                          CommentService commentService,
                          ReplyService replyService,
                          ManufacturerService manufacturerService,
                          CarmodelService carmodelService) {
        this.spotService = spotService;
        this.countryService = countryService;
        this.fileUpload = fileUpload;
        this.modificationService = modificationService;
        this.userService = userService;
        this.commentService = commentService;
        this.replyService = replyService;
        this.manufacturerService = manufacturerService;
        this.carmodelService = carmodelService;
    }

    private static final String DATE_FORMAT_NOW1 = "dd.MM.yyyy";
    private static final String DATE_FORMAT_NOW2 = "dd.MM.yyyy HH:mm";

    public static String date() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW1);
        return sdf.format(cal.getTime());
    }

    public static String dateAndTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW2);
        return sdf.format(cal.getTime());
    }

    @GetMapping("/spotAdd")
    public String spotAdd() {
        return "spotAdd";
    }

    @GetMapping("/spotCreate")
    public String spotCreateForm(Spot spot, Model model) {
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        List<Manufacturer> manufacturers = manufacturerService.findAllByOrderByNameAsc();
        model.addAttribute("manufacturers", manufacturers);
        List<Carmodel> carmodels = carmodelService.findAllByOrderByNameAsc();
        model.addAttribute("carmodels", carmodels);
        return "spotCreate";
    }

    @PostMapping("/spotCreate")
    public String spotCreate(Spot spot,
                             @AuthenticationPrincipal User user,
                             @RequestParam("file1") MultipartFile file1,
                             @RequestParam("file2") MultipartFile file2,
                             @RequestParam("file3") MultipartFile file3
    ) throws IOException {
        fileUpload.uploadFile(spot, file1);
        if (resultFilename != "empty") {
            spot.setPhoto1(resultFilename);
        }

        fileUpload.uploadFile(spot, file2);
        if (resultFilename != "empty") {
            spot.setPhoto2(resultFilename);
        }

        fileUpload.uploadFile(spot, file3);
        if (resultFilename != "empty") {
            spot.setPhoto3(resultFilename);
        }

        spot.setUser(user);
        spot.setDate(date());
        spotService.save(spot);
        return "redirect:/";
    }

    @GetMapping("/spotOpen/{id}")
    public String spotOpenForm(@AuthenticationPrincipal User user,
                               @PathVariable Long id,
                               Comment comment,
                               Model model) {

        Spot spot = spotService.findById(id);
        model.addAttribute("spot", spot);

        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }

        List<Comment> comments = commentService.findBySpotIdOrderByIdAsc(id);
        model.addAttribute("comments", comments);

        List<Reply> replies = replyService.findBySpotIdOrderByIdAsc(id);
        model.addAttribute("replies",replies);

        return "spotOpen";
    }

    @PostMapping("/spotOpen")
    public String spotOpen(@AuthenticationPrincipal User user,
                           Comment comment) {
        User user1 = userService.findById(user.getId());

        comment.setSpot(spotService.findById(user1.getTmp()));
        comment.setUser(user1);
        comment.setDate(dateAndTime());

        if (user1.getRating() == null) {
            user1.setRating(1);
        } else {
            user1.setRating(user1.getRating() + 1);
        }

        commentService.save(comment);
        return "redirect:/spotOpen/" + user1.getTmp();
    }

    @GetMapping("/spotUpdate/{id}")
    public String spotUpdateForm(@PathVariable Long id,
                                 Model model) {

        Spot spot = spotService.findById(id);
        model.addAttribute("spot", spot);

        List<Modification> modifications = modificationService.findAllByOrderByIdAsc();
        model.addAttribute("modifications", modifications);

        List<Comment> comments = commentService.findBySpotIdOrderByIdAsc(id);
        model.addAttribute("comments", comments);

        List<Reply> replies = replyService.findBySpotIdOrderByIdAsc(id);
        model.addAttribute("replies",replies);

        return "spotUpdate";
    }

    @PostMapping("/spotUpdate")
    public String spotUpdate(Spot spot) {

        spotService.save(spot);
        return "redirect:/adminPage";
    }

    @GetMapping("/spots")
    public String spots(@AuthenticationPrincipal User user, Model model) {
        List<SpotDto> spots = spotService.findAllByOrderByIdDesc(user);
        model.addAttribute("spots", spots);
        return "spots";
    }

    @GetMapping("userOpen/{id}")
    public String userOpen(@AuthenticationPrincipal User user,
                           @PathVariable Long id,
                           Model model) {
        User user1 = userService.findById(id);
        List<SpotDto> spots = spotService.findByUserIdOrderByIdDesc(user1, user);
        model.addAttribute("spots", spots);
        return "userOpen";
    }
}
