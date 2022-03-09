package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Tuner;
import com.loripin.auto.model.User;
import com.loripin.auto.service.CountryService;
import com.loripin.auto.service.TunerService;
import com.loripin.auto.service.UserService;
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
import java.util.List;
import java.util.UUID;

import static com.loripin.auto.controller.ModificationController.existingPhoto;

@Controller
public class TunerController {
    private final
    UserService userService;
    private final
    CountryService countryService;
    private final
    TunerService tunerService;

    public TunerController(TunerService tunerService, CountryService countryService, UserService userService) {
        this.tunerService = tunerService;
        this.countryService = countryService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/tunerList")
    public String listTuner(Model model) {
        model.addAttribute("tuners", tunerService.findAllByOrderByName());
        return "tunerList";
    }

    @GetMapping("/tunerCreate")
    public String createTunerForm(Tuner tuner, Model model){
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "tunerCreate";
    }

    @PostMapping("/tunerCreate")
    public String createTuner(Tuner tuner,
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

            tuner.setLogo(resultFilename);
        }
        tunerService.save(tuner);
        return "redirect:/tunerList";
    }

    @GetMapping("/tunerUpdate/{id}")
    public String updateTunerForm(@AuthenticationPrincipal User user,
                                  @PathVariable Long id,
                                  Model model){

        Tuner tuner = tunerService.findById(id);
        model.addAttribute("tuner", tuner);
        user.setTmp(tuner.getId());
        userService.save(user);
        existingPhoto = tuner.getLogo();
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "tunerUpdate";
    }

    @PostMapping("tunerUpdate")
    public String updateTuner(@AuthenticationPrincipal User user,
                              Tuner tuner,
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
                tuner.setLogo(existingPhoto);
            } else {
                tuner.setLogo(resultFilename);
            }
        }
        tunerService.save(tuner);
        User user1 = userService.findById(user.getId());
        return "redirect:/tunerOpen/" + user1.getTmp();
    }

    @GetMapping("/tunerOpen/{id}")
    public String openTuner(@PathVariable("id") Long id, Model model) {
        Tuner tuner = tunerService.findById(id);
        model.addAttribute("tuner", tuner);
        return "tunerOpen";
    }
}
