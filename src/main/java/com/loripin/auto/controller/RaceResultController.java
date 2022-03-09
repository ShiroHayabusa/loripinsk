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
public class RaceResultController {
    private final
    UserService userService;
    private final
    QualificationService qualificationService;
    private final
    ModificationService modificationService;
    private final
    RaceResultService raceResultService;
    private final
    RaceClassService raceClassService;
    private final
    TeamService teamService;
    private final
    PersonService personService;
    private final
    RaceService raceService;

    public RaceResultController(RaceService raceService,
                                PersonService personService,
                                TeamService teamService,
                                RaceClassService raceClassService,
                                RaceResultService raceResultService,
                                ModificationService modificationService,
                                QualificationService qualificationService,
                                UserService userService) {
        this.raceService = raceService;
        this.personService = personService;
        this.teamService = teamService;
        this.raceClassService = raceClassService;
        this.raceResultService = raceResultService;
        this.modificationService = modificationService;
        this.qualificationService = qualificationService;
        this.userService = userService;
    }

    @GetMapping("/raceResultList")
    public String raceResultList(Model model) {
        List<RaceResult> raceResults = raceResultService.findAllByOrderByIdAsc();
        model.addAttribute("raceResults", raceResults);
        return "raceResultList";
    }

    @GetMapping("/raceResultCreate")
    public String raceResultCreateForm(@AuthenticationPrincipal User user,
                                       RaceResult raceResult,
                                       Model model) {
        User user1 = userService.findById(user.getId());
        Race race = raceService.findById(user1.getTmp());
        List<Modification> modifications = modificationService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("modifications", modifications);
        List<RaceClass> raceClasses = raceClassService.findAllByOrderByIdAsc();
        model.addAttribute("raceClasses", raceClasses);
        List<Team> teams = teamService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("teams", teams);
        List<Person> persons = personService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("persons", persons);
        model.addAttribute("race",raceService.findById(user1.getTmp()));
        return "raceResultCreate";
    }

    @PostMapping("/raceResultCreate")
    public String raceResultCreate(@AuthenticationPrincipal User user,
                                   RaceResult raceResult) {
        User user1 = userService.findById(user.getId());
        raceResult.setRace(raceService.findById(user1.getTmp()));
        raceResult.setSeries(raceService.findById(user1.getTmp()).getSeries());
        raceResultService.save(raceResult);
        return "redirect:/autosport/raceResult/" + user1.getTmp();
    }

    @GetMapping("/raceResultUpdate/{id}")
    public String raceResultUpdateForm(@AuthenticationPrincipal User user,
                                       @PathVariable Long id,
                                       Model model) {
        RaceResult raceResult = raceResultService.findById(id);
        model.addAttribute("raceResult", raceResult);

        User user1 = userService.findById(user.getId());
        Race race = raceService.findById(user1.getTmp());
        List<Modification> modifications = modificationService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("modifications", modifications);
        List<RaceClass> raceClasses = raceClassService.findAllByOrderByIdAsc();
        model.addAttribute("raceClasses", raceClasses);
        List<Team> teams = teamService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("teams", teams);
        List<Person> persons = personService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("persons", persons);
        model.addAttribute("race",raceService.findById(user1.getTmp()));
        return "raceResultUpdate";
    }

    @PostMapping("/raceResultUpdate")
    public String raceResultUpdate(@AuthenticationPrincipal User user,
                                   RaceResult raceResult) {
        User user1 = userService.findById(user.getId());
        raceResultService.save(raceResult);
        return "redirect:/autosport/raceResult/" + user1.getTmp();
    }

    @GetMapping("/autosport/raceResult/{id}")
    public String seriesResults(@AuthenticationPrincipal User user,
                                @PathVariable("id") Long id,
                                Model model) {
        Race race = raceService.findById(id);
        model.addAttribute("race", race);
        if (user != null) {
            user.setTmp(race.getId());
            userService.save(user);
        }
        List<RaceResult> raceResults = raceResultService.findByRaceIdOrderByPosition(id);
        model.addAttribute("raceResults", raceResults);
        model.addAttribute("series", race.getSeries());

        List<Qualification> qualifications = qualificationService.findByRaceIdOrderByPosition(id);
        model.addAttribute("qualifications", qualifications);
        return "raceResult";
    }

    @GetMapping("/qualificationCreate")
    public String qualificationCreateForm(@AuthenticationPrincipal User user,
                                          Qualification qualification,
                                          Model model) {
        User user1 = userService.findById(user.getId());
        Race race = raceService.findById(user1.getTmp());
        List<Modification> modifications = modificationService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("modifications", modifications);
        List<Team> teams = teamService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("teams", teams);
        List<Person> persons = personService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("persons", persons);
        model.addAttribute("race",raceService.findById(user1.getTmp()));
        return "qualificationCreate";
    }

    @PostMapping("/qualificationCreate")
    public String qualificationCreate(@AuthenticationPrincipal User user,
                                      Qualification qualification) {
        User user1 = userService.findById(user.getId());
        qualification.setRace(raceService.findById(user1.getTmp()));
        qualification.setSeries(raceService.findById(user1.getTmp()).getSeries());
        qualificationService.save(qualification);
        return "redirect:/autosport/raceResult/" + user1.getTmp();
    }

    @GetMapping("/qualificationUpdate/{id}")
    public String qualificationUpdateForm(@AuthenticationPrincipal User user,
                                          @PathVariable Long id,
                                          Model model) {
        Qualification qualification = qualificationService.findById(id);
        model.addAttribute("qualification", qualification);

        User user1 = userService.findById(user.getId());
        Race race = raceService.findById(user1.getTmp());
        List<Modification> modifications = modificationService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("modifications", modifications);
        List<Team> teams = teamService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("teams", teams);
        List<Person> persons = personService.findBySeriesIdOrderByNameAsc(race.getSeries().getId());
        model.addAttribute("persons", persons);
        model.addAttribute("race",raceService.findById(user1.getTmp()));
        return "qualificationUpdate";
    }

    @PostMapping("/qualificationUpdate")
    public String qualificationUpdate(@AuthenticationPrincipal User user,
                                      Qualification qualification) {
        User user1 = userService.findById(user.getId());
        qualificationService.save(qualification);
        return "redirect:/autosport/raceResult/" + user1.getTmp();
    }
}
