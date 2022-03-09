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
public class CarmodelController {
    private final
    UserService userService;
    private final
    DriveService driveService;
    private final
    GearBoxService gearBoxService;
    private final
    EngineService engineService;
    private final
    GenerationService generationService;
    private final
    ManufacturerService manufacturerService;
    private final
    CarmodelService carmodelService;

    public CarmodelController(CarmodelService carmodelService,
                              ManufacturerService manufacturerService,
                              GenerationService generationService,
                              EngineService engineService,
                              GearBoxService gearBoxService,
                              DriveService driveService,
                              UserService userService) {
        this.carmodelService = carmodelService;
        this.manufacturerService = manufacturerService;
        this.generationService = generationService;
        this.engineService = engineService;
        this.gearBoxService = gearBoxService;
        this.driveService = driveService;
        this.userService = userService;
    }

    public static List<String> existingUniqPhotos;

    @GetMapping("/carmodelCreate")
    public String createCarmodelForm(@AuthenticationPrincipal User user,
                                     Carmodel carmodel,
                                     Model model) {

        User user1 = userService.findById(user.getId());

        Manufacturer manufacturer = manufacturerService.findById(user1.getTmp());
        model.addAttribute("manufacturer", manufacturer);
        return "carmodelCreate";
    }

    @PostMapping("/carmodelCreate")
    public String createCarmodel(@AuthenticationPrincipal User user,
                                 Carmodel carmodel) {

        User user1 = userService.findById(user.getId());

        carmodel.setManufacturer(manufacturerService.findById(user1.getTmp()));
        carmodelService.saveCarmodel(carmodel);
        return "redirect:/catalog/manufacturer/" + user1.getTmp();
    }

    @GetMapping("/carmodelUpdate/{id}")
    public String carmodelUpdateForm(@AuthenticationPrincipal User user,
                                     @PathVariable Long id,
                                     Model model){
        Carmodel carmodel = carmodelService.findById(id);
        model.addAttribute("carmodel", carmodel);
        user.setTmp(id);
        userService.save(user);
        return "carmodelUpdate";
    }

    @PostMapping("carmodelUpdate")
    public String carmodelUpdate(@AuthenticationPrincipal User user,
                                 Carmodel carmodel) {
        carmodelService.saveCarmodel(carmodel);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/" + user1.getTmp();
    }

    @GetMapping("uniqUpdate/{id}")
    public String updateUniqForm(@PathVariable("id") Long id,
                                 Model model) {
        List<Engine> engines = engineService.findAllByOrderByIdAsc();
        model.addAttribute("engines", engines);
        List<GearBox> gearBoxes = gearBoxService.findAllByOrderByIdAsc();
        model.addAttribute("gearBoxes", gearBoxes);
        List<Drive> drives = driveService.findAllByOrderByIdAsc();
        model.addAttribute("drives", drives);
        Carmodel carmodel = carmodelService.findById(id);
        model.addAttribute("carmodel", carmodel);
        return "uniqUpdate";
    }

    @GetMapping("/catalog/manufacturer/carmodel/{id}")
    public String openCarmodel(@AuthenticationPrincipal User user,
                               @PathVariable("id") Long id,
                               Model model) {
        Carmodel carmodel = carmodelService.findById(id);
        model.addAttribute("carmodel", carmodel);

        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }

        List<Generation> generations = generationService.findByCarmodelIdOrderByYearsAsc(id);
        model.addAttribute("generations", generations);
        return "generations";
    }

    @GetMapping("/catalog/manufacturer/carmodel/uniq/{id}")
    public String uniqOpen(@PathVariable Long id,
                           Model model) {
        Carmodel carmodel = carmodelService.findById(id);
        model.addAttribute("carmodel", carmodel);
        return "uniqOpen";
    }

    @GetMapping("/catalog/manufacturer/carmodel/uniq/delete/{id}")
    public String uniqDelete(@AuthenticationPrincipal User user,
                             @PathVariable Long id) {
        carmodelService.deleteById(id);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/" + user1.getTmp();
    }
}
