package com.loripin.auto.controller;

import com.loripin.auto.model.RaceClass;
import com.loripin.auto.model.Series;
import com.loripin.auto.service.RaceClassService;
import com.loripin.auto.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RaceClassController {
    private final
    SeriesService seriesService;
    private final
    RaceClassService raceClassService;

    public RaceClassController(RaceClassService raceClassService,
                               SeriesService seriesService) {
        this.raceClassService = raceClassService;
        this.seriesService = seriesService;
    }

    @GetMapping("/raceClassList")
    public String raceClassList(Model model) {
        List<RaceClass> raceClasses = raceClassService.findAllByOrderByIdAsc();
        model.addAttribute("raceClasses", raceClasses);
        return "raceClassList";
    }

    @GetMapping("/raceClassCreate")
    public String raceClassCreateForm(RaceClass raceClass,
                                      Model model) {
        List<Series> series = seriesService.findAll();
        model.addAttribute("series", series);
        return "raceClassCreate";
    }

    @PostMapping("/raceClassCreate")
    public String raceClassCreate(RaceClass raceClass) {
        raceClassService.save(raceClass);
        return "redirect:/raceClassList";
    }
}
