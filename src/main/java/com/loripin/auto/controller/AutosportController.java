package com.loripin.auto.controller;

import com.loripin.auto.model.Series;
import com.loripin.auto.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AutosportController {
    private final
    SeriesService seriesService;

    public AutosportController(SeriesService seriesService) {
        this.seriesService = seriesService;
    }

    @GetMapping("/autosport")
    public String Autosport(Model model) {
        List<Series> series = seriesService.findAllByOrderByIdAsc();
        model.addAttribute("series", series);
        return "autosport";
    }
}
