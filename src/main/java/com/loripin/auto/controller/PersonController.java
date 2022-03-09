package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.service.*;
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
import java.util.List;
import java.util.UUID;

import static com.loripin.auto.controller.ModificationController.existingPhoto;

@Controller
public class PersonController {
    private final
    UserService userService;
    private final
    TeamService teamService;
    private final
    PersonService personService;
    private final
    SeriesService seriesService;
    private final
    CountryService countryService;

    public PersonController(CountryService countryService,
                            SeriesService seriesService,
                            PersonService personService,
                            TeamService teamService,
                            UserService userService) {
        this.countryService = countryService;
        this.seriesService = seriesService;
        this.personService = personService;
        this.teamService = teamService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/personList")
    public String personList(Model model) {
        List<Person> persons = personService.findAllByOrderByNameAsc();
        model.addAttribute("persons", persons);
        return "personList";
    }

    @GetMapping("/personCreate")
    public String personCreateForm(Person person,
                                   Model model) {
        List<Team> teams = teamService.findAllByOrderByNameAsc();
        List<Country> countries = countryService.findAllByOrderByName();
        List<Series> series = seriesService.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        model.addAttribute("teams", teams);
        return "personCreate";
    }

    @PostMapping("/personCreate")
    public String personCreate(@AuthenticationPrincipal User user,
                               Person person,
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

            person.setPhoto(resultFilename);
        }
        personService.save(person);

        User user1 = userService.findById(user.getId());

        return "redirect:/autosport/persons/" + user1.getTmp();
    }

    @GetMapping("/personUpdate/{id}")
    public String personUpdateForm(@AuthenticationPrincipal User user,
                                   @PathVariable Long id, Model model) {
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        List<Country> countries = countryService.findAll();
        List<Series> series = seriesService.findAll();
        List<Team> teams = teamService.findAllByOrderByNameAsc();
        model.addAttribute("countries", countries);
        model.addAttribute("series", series);
        model.addAttribute("teams", teams);

        user.setTmp(id);
        userService.save(user);

        existingPhoto = person.getPhoto();
        return "personUpdate";
    }

    @PostMapping("/personUpdate")
    public String personUpdate(@AuthenticationPrincipal User user,
                               Person person,
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
                person.setPhoto(existingPhoto);
            } else {
                person.setPhoto(resultFilename);
            }
        }
        personService.save(person);

        User user1 = userService.findById(user.getId());

        return "redirect:/personOpen/" + user1.getTmp();
    }

    @GetMapping("/personOpen/{id}")
    public String personOpen(@PathVariable("id") Long id,
                             Model model){
        Person person = personService.findById(id);
        model.addAttribute("person", person);
        return "personOpen";
    }

    @GetMapping("/autosport/persons/{id}")
    public String Persons(@AuthenticationPrincipal User user,
                          @PathVariable("id") Long id,
                          Model model){
        Series series = seriesService.findById(id);
        model.addAttribute("series", series);

        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }

        List<Person> persons = personService.findBySeriesIdOrderByIdAsc(id);
        model.addAttribute("persons", persons);
        return "persons";
    }
}
