package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Track;
import com.loripin.auto.service.CountryService;
import com.loripin.auto.service.TrackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TrackController {
    private final
    CountryService countryService;
    private final
    TrackService trackService;

    public TrackController(TrackService trackService, CountryService countryService) {
        this.trackService = trackService;
        this.countryService = countryService;
    }

    @GetMapping("/trackCreate")
    public String trackCreateForm(Track track, Model model) {
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "trackCreate";
    }

    @PostMapping("/trackCreate")
    public String trackCreate(Track track) {
        trackService.save(track);
        return "redirect:/trackList";
    }

    @GetMapping("/trackList")
    public String trackList(Model model) {
        List<Track> tracks = trackService.findAll();
        model.addAttribute("tracks", tracks);
        return "trackList";
    }

    @GetMapping("/trackUpdate/{id}")
    public String trackUpdateForm(@PathVariable Long id, Model model){
        Track track = trackService.findById(id);
        model.addAttribute("track", track);
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "trackUpdate";
    }

    @PostMapping("trackUpdate")
    public String trackUpdate(Track track) {
        trackService.save(track);
        return "redirect:/trackList";
    }

    @GetMapping("/trackDelete/{id}")
    public String trackDelete(@PathVariable("id") Long id) {
        trackService.deleteById(id);
        return "redirect:/trackList";
    }
}
