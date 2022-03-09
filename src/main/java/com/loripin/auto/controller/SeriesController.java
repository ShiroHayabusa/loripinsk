package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SeriesController {
    private final
    UserService userService;
    private final
    TeamService teamService;
    private final
    PersonService personService;
    private final
    RaceResultService raceResultService;
    private final
    RaceService raceService;
    private final
    SeriesService seriesService;

    public SeriesController(SeriesService seriesService,
                            RaceService raceService,
                            RaceResultService raceResultService,
                            PersonService personService,
                            TeamService teamService,
                            UserService userService) {
        this.seriesService = seriesService;
        this.raceService = raceService;
        this.raceResultService = raceResultService;
        this.personService = personService;
        this.teamService = teamService;
        this.userService = userService;
    }

    public static String existingPhoto;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/seriesCreate")
    public String seriesCreateForm(Series series) {
        return "seriesCreate";
    }

    @PostMapping("/seriesCreate")
    public String seriesCreate(Series series, @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            series.setPhoto(resultFilename);
        }
        seriesService.save(series);
        return "redirect:/seriesList";
    }

    @GetMapping("seriesUpdate/{id}")
    public String updateSeriesForm(@PathVariable("id") Long id, Model model) {
        Series series = seriesService.findById(id);
        existingPhoto = series.getPhoto();
        model.addAttribute("series", series);
        return "seriesUpdate";
    }

    @PostMapping("/seriesUpdate")
    public String updateGoods(Series series, @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            if (file.isEmpty()) {
                series.setPhoto(existingPhoto);
            } else {
                series.setPhoto(resultFilename);
            }
        }
        seriesService.save(series);
        return "redirect:/seriesList";
    }

    @GetMapping("/seriesList")
    public String seriesList(Model model) {
        List<Series> series = seriesService.findAllByOrderByIdAsc();
        model.addAttribute("series", series);
        return "seriesList";
    }

    @GetMapping("/autosport/{id}")
    public String seriesOpen(@PathVariable("id") Long id, Model model) {
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);
        List<RaceResult> raceResults = raceResultService.findBySeriesIdOrderByIdAsc(id);
        List<Person> tmpPilots = personService.findBySeriesIdOrderByPoints2021Desc(id);


        for (Person pilot: tmpPilots) {
            Integer points = 0;
            for (RaceResult raceResult: raceResults) {
                if (pilot == raceResult.getPilot()) {
                    if (raceResult.getBonus() != null && raceResult.getPoints() != null) {
                        points = points + raceResult.getPoints() + raceResult.getBonus();
                    } else {
                        if (raceResult.getBonus() != null && raceResult.getPoints() == null) {
                            points = points + raceResult.getBonus();
                        } else {
                            if (raceResult.getBonus() == null && raceResult.getPoints() != null) {
                                points = points + raceResult.getPoints();
                            }
                        }
                    }
                }
            }
            pilot.setPoints2021(points);
        }

        List<Person> pilots = personService.findBySeriesIdOrderByPoints2021Desc(id);
        model.addAttribute("pilots", pilots);

        List<Team> teams = new ArrayList<>();
        List<Team> tmpTeams = teamService.findBySeriesIdOrderByPoints2021Desc(id);
        for (Team team: tmpTeams) {
            if (team.getPoints2021() != null) {
                teams.add(team);
            }
        }
        model.addAttribute("teams", teams);

        return "seriesOpen";
    }

    @GetMapping("/autosport/calendar/{id}")
    public String seriesCalendar(@AuthenticationPrincipal User user,
                                 @PathVariable("id") Long id,
                                 Model model) {
        List<Race> races = raceService.findBySeriesIdAndSeasonOrderByStageNumber(id, "2021");
        model.addAttribute("races", races);
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);
        if (user != null) {
            user.setTmp(series.getId());
            userService.save(user);
        }
        return "seriesCalendar";
    }

    @GetMapping("/autosport/results/{id}")
    public String seriesResults(@PathVariable("id") Long id, Model model) {
        List<Race> races = raceService.findBySeriesIdAndSeasonOrderByStageNumber(id, "2021");
        model.addAttribute("races", races);
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);
        return "seriesResults";
    }

    @GetMapping("/autosport/archive/{id}")
    public String seriesArchive(@PathVariable("id") Long id, Model model) {
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);
        return "seriesArchive";
    }
}
