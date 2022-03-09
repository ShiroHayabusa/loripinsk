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
public class ManufacturerController {
    private final
    UserService userService;
    private final
    ModificationService modificationService;
    private final
    CarmodelService carmodelService;
    private final
    CountryService countryService;
    private final
    ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService,
                                  CountryService countryService,
                                  CarmodelService carmodelService,
                                  ModificationService modificationService,
                                  UserService userService) {
        this.manufacturerService = manufacturerService;
        this.countryService = countryService;
        this.carmodelService = carmodelService;
        this.modificationService = modificationService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/catalog")
    public String showCatalog(Model model) {
        model.addAttribute("manufacturers", manufacturerService.findAllByOrderByNameAsc());
        return "catalog";
    }

    @GetMapping("/manufacturerCreate")
    public String createManufacturerForm(Manufacturer manufacturer,
                                         Model model){
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "manufacturerCreate";
    }

    @PostMapping("/manufacturerCreate")
    public String createManufacturer(Manufacturer manufacturer,
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

            manufacturer.setLogo(resultFilename);
        }
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/catalog";
    }

    @GetMapping("/manufacturerUpdate/{id}")
    public String manufacturerUpdateForm(@AuthenticationPrincipal User user,
                                         @PathVariable Long id,
                                         Model model){
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer", manufacturer);

        user.setTmp(id);
        userService.save(user);

        existingPhoto = manufacturer.getLogo();
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
        return "manufacturerUpdate";
    }

    @PostMapping("manufacturerUpdate")
    public String manufacturerUpdate(@AuthenticationPrincipal User user,
                                     Manufacturer manufacturer,
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
                manufacturer.setLogo(existingPhoto);
            } else {
                manufacturer.setLogo(resultFilename);
            }
        }
        manufacturerService.saveManufacturer(manufacturer);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/" + user1.getTmp();
    }

    @GetMapping("/catalog/manufacturer/{id}")
    public String openManufacturer(@PathVariable("id") Long id,
                                   @AuthenticationPrincipal User user,
                                   Model model) {
        Manufacturer manufacturer = manufacturerService.findById(id);
        model.addAttribute("manufacturer",manufacturer);

        List<Carmodel> carmodels = carmodelService.findByManufacturerIdOrderByName(id);
        model.addAttribute("carmodels", carmodels);

        List<Modification> modifications = modificationService.findAll();
        model.addAttribute("modifications", modifications);
        if (user != null) {

            user.setTmp(id);
            userService.save(user);

        }
        return "manufacturerOpen";
    }
}

