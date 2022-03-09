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

import javax.persistence.Access;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.loripin.auto.controller.ModificationController.existingPhoto;

@Controller
public class TeamController {
    private final
    UserService userService;
    private final
    PersonService personService;
    private final
    SeriesService seriesService;
    private final
    CountryService countryService;
    private final
    TeamService teamService;

    public TeamController(TeamService teamService,
                          CountryService countryService,
                          SeriesService seriesService,
                          PersonService personService,
                          UserService userService) {
        this.teamService = teamService;
        this.countryService = countryService;
        this.seriesService = seriesService;
        this.personService = personService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/teamCreate")
    public String personCreateForm(Team team, Model model) {
        List<Country> countries = countryService.findAll();
        List<Series> series = seriesService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        return "teamCreate";
    }

    @PostMapping("/teamCreate")
    public String teamCreate(@AuthenticationPrincipal User user,
                             Team team,
                             @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file != null) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            team.setLogo(resultFilename);
        }
        teamService.save(team);
        User user1 = userService.findById(user.getId());
        return "redirect:/autosport/teams/" + user1.getTmp();
    }

    @GetMapping("/teamUpdate/{id}")
    public String teamUpdateForm(@AuthenticationPrincipal User user,
                                 @PathVariable Long id,
                                 Model model) {
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        List<Country> countries = countryService.findAll();
        List<Series> series = seriesService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        existingPhoto = team.getLogo();
        user.setTmp(team.getId());
        userService.save(user);
        return "teamUpdate";
    }

    @PostMapping("/teamUpdate")
    public String teamUpdate(@AuthenticationPrincipal User user,
                             Team team,
                             @RequestParam("file") MultipartFile file
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
                team.setLogo(existingPhoto);
            } else {
                team.setLogo(resultFilename);
            }
        }
        teamService.save(team);
        User user1 = userService.findById(user.getId());
        return "redirect:/teamOpen/" + user1.getTmp();
    }

    @GetMapping("/teamOpen/{id}")
    public String teamOpen(@PathVariable("id") Long id, Model model){
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        List<Person> pilots = personService.findByTeamIdAndNavigatorOrderByNameAsc(id, null);
        model.addAttribute("pilots", pilots);
        List<Person> navigators = personService.findByTeamIdAndNavigatorOrderByNameAsc(id, true);
        model.addAttribute("navigators", navigators);
        return "teamOpen";
    }

    @GetMapping("/autosport/teams/{id}")
    public String Teams(@AuthenticationPrincipal User user,
                        @PathVariable("id") Long id,
                        Model model){
        Series series = seriesService.findById(id);

        if (user != null) {
            user.setTmp(series.getId());
            userService.save(user);
        }

        model.addAttribute("series", series);
        List<Team> teams = teamService.findBySeriesIdOrderByIdAsc(id);
        model.addAttribute("teams", teams);
        return "teams";
    }

}
