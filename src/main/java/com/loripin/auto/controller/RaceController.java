package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RaceController {
    private final
    UserService userService;
    private final
    RaceService raceService;
    private final
    TrackService trackService;
    private final
    SeriesService seriesService;
    private final
    CountryService countryService;

    public RaceController(CountryService countryService,
                          SeriesService seriesService,
                          TrackService trackService,
                          RaceService raceService,
                          UserService userService) {
        this.countryService = countryService;
        this.seriesService = seriesService;
        this.trackService = trackService;
        this.raceService = raceService;
        this.userService = userService;
    }

    @GetMapping("/raceCreate")
    public String raceCreateForm(@AuthenticationPrincipal User user,
                                 Race race,
                                 Model model) {
        List<Country> countries = countryService.findAllByOrderByName();
        List<Series> series = seriesService.findAll();
        List<Track> tracks = trackService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        model.addAttribute("tracks", tracks);

        User user1 = userService.findById(user.getId());

        model.addAttribute("series", seriesService.findById(user1.getTmp()));
        return "raceCreate";
    }

    @PostMapping("/raceCreate")
    public String raceCreate(@AuthenticationPrincipal User user,
                             Race race) {

        User user1 = userService.findById(user.getId());

        race.setSeries(seriesService.findById(user1.getTmp()));
        raceService.save(race);
        return "redirect:/autosport/calendar/" + user1.getTmp();
    }

    @GetMapping("/raceUpdate/{id}")
    public String raceUpdateForm(@PathVariable Long id,
                                 Model model) {
        Race race = raceService.findById(id);
        model.addAttribute("race",race);

        List<Country> countries = countryService.findAllByOrderByName();
        List<Series> series = seriesService.findAllByOrderByName();
        List<Track> tracks = trackService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        model.addAttribute("tracks", tracks);
        return "raceUpdate";
    }

    @PostMapping("/raceUpdate")
    public String raceUpdate(Race race) {
        raceService.save(race);
        return "redirect:/raceList";
    }

    @GetMapping("/raceList")
    public String raceList(Model model) {
        List<Race> races = raceService.findAll();
        model.addAttribute("races", races);
        return "raceList";
    }
}
