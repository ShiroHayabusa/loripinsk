package com.loripin.auto.controller;

import com.loripin.auto.model.Body;
import com.loripin.auto.model.BodyType;
import com.loripin.auto.model.Generation;
import com.loripin.auto.model.User;
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
public class GenerationController {
    private final
    CarmodelService carmodelService;
    private final
    UserService userService;
    private final
    BodyTypeService bodyTypeService;
    private final
    BodyService bodyService;
    private final
    GenerationService generationService;

    public GenerationController(GenerationService generationService,
                                BodyService bodyService,
                                BodyTypeService bodyTypeService,
                                UserService userService,
                                CarmodelService carmodelService) {
        this.generationService = generationService;
        this.bodyService = bodyService;
        this.bodyTypeService = bodyTypeService;
        this.userService = userService;
        this.carmodelService = carmodelService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/generationCreate")
    public String createGenerationForm(@AuthenticationPrincipal User user,
                                       Generation generation,
                                       Model model){
        List<Body> bodies = bodyService.findAllByOrderByNameAsc();
        model.addAttribute("bodies", bodies);

        User user1 = userService.findById(user.getId());

        model.addAttribute("carmodel", carmodelService.findById(user1.getTmp()));
        return "generationCreate";
    }

    @PostMapping("/generationCreate")
    public String createGeneration(@AuthenticationPrincipal User user,
                                   Generation generation,
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

            generation.setPhoto(resultFilename);
        }

        User user1 = userService.findById(user.getId());

        generation.setManufacturer(carmodelService.findById(user1.getTmp()).getManufacturer());
        generation.setCarmodel(carmodelService.findById(user1.getTmp()));
        generationService.saveGeneration(generation);
        return "redirect:/catalog/manufacturer/carmodel/" + user1.getTmp();
    }

    @GetMapping("/generationUpdate/{id}")
    public String generationUpdateForm(@AuthenticationPrincipal User user,
                                       @PathVariable Long id,
                                       Model model){
        List<Body> bodies = bodyService.findAllByOrderByNameAsc();
        model.addAttribute("bodies", bodies);
        Generation generation = generationService.findById(id);
        model.addAttribute("generation", generation);
        user.setTmp(id);
        userService.save(user);
        existingPhoto = generation.getPhoto();
        return "generationUpdate";
    }

    @PostMapping("generationUpdate")
    public String generationUpdate(@AuthenticationPrincipal User user,
                                   Generation generation,
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
                generation.setPhoto(existingPhoto);
            } else {
                generation.setPhoto(resultFilename);
            }
        }
        generationService.saveGeneration(generation);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/generation/" + user1.getTmp();
    }

    @GetMapping("/catalog/manufacturer/carmodel/generation/{id}")
    public String openGeneration(@AuthenticationPrincipal User user,
                                 @PathVariable("id") Long id,
                                 Model model) {
        List<BodyType> restyles0 = bodyTypeService.findByGenerationIdAndRestyleName(id, null);
        model.addAttribute("restyles0", restyles0);
        List<BodyType> restyles1 = bodyTypeService.findByGenerationIdAndRestyleName(id, "Facelift");
        model.addAttribute("restyles1", restyles1);
        List<BodyType> restyles2 = bodyTypeService.findByGenerationIdAndRestyleName(id, "2nd facelift");
        model.addAttribute("restyles2", restyles2);
        List<BodyType> restyles3 = bodyTypeService.findByGenerationIdAndRestyleName(id, "3rd facelift");
        model.addAttribute("restyles3", restyles3);
        List<BodyType> restyles4 = bodyTypeService.findByGenerationIdAndRestyleName(id, "4th facelift");
        model.addAttribute("restyles4", restyles4);
        List<BodyType> restyles5 = bodyTypeService.findByGenerationIdAndRestyleName(id, "5th facelift");
        model.addAttribute("restyles5", restyles5);
        Generation generation = generationService.findById(id);
        model.addAttribute("generation", generation);
        if (user != null) {
            user.setTmp(id);
            userService.save(user);
        }
        return "restyles";
    }

    @GetMapping("/manufacturer/carmodel/generation/delete/{id}")
    public String deleteGeneration(@AuthenticationPrincipal User user,
                                   @PathVariable("id") Long id) {
        generationService.deleteById(id);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/" + user1.getTmp();
    }
}
