package com.loripin.auto.controller;

import com.loripin.auto.model.Title;
import com.loripin.auto.service.TitleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TitleController {
    private final
    TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping("/titleList")
    public String titleList(Model model) {
        List<Title> titles = titleService.findAllByOrderByIdAsc();
        model.addAttribute("titles", titles);
        return "titleList";
    }

    @GetMapping("/titleCreate")
    public String titleCreateForm(Title title) {
        return "titleCreate";
    }

    @PostMapping("/titleCreate")
    public String titleCreate(Title title) {
        titleService.save(title);
        return "redirect:/titleList";
    }
}
