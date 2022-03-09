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

@Controller
public class BodyTypeController {
    private final
    UserService userService;
    private final
    SpecService specService;
    private final
    BodyTypeNameService bodyTypeNameService;
    private final
    GenerationService generationService;
    private final
    RestyleService restyleService;
    private final
    BodyTypeService bodyTypeService;

    public BodyTypeController(BodyTypeService bodyTypeService,
                              RestyleService restyleService,
                              GenerationService generationService,
                              BodyTypeNameService bodyTypeNameService,
                              SpecService specService,
                              UserService userService) {
        this.bodyTypeService = bodyTypeService;
        this.restyleService = restyleService;
        this.generationService = generationService;
        this.bodyTypeNameService = bodyTypeNameService;
        this.specService = specService;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/bodyTypeList")
    public String bodyTypeList(Model model) {
        List<BodyType> bodyTypes = bodyTypeService.findAllByOrderByIdAsc();
        model.addAttribute("bodyTypes", bodyTypes);
        return "bodyTypeList";
    }

    @GetMapping("/bodyTypeCreate")
    public String bodyTypeCreateForm(@AuthenticationPrincipal User user,
                                     BodyType bodyType,
                                     Model model) {

        User user1 = userService.findById(user.getId());

        List<Spec> specs = specService.findAllByOrderByName();
        model.addAttribute("specs", specs);

        List<BodyTypeName> bodyTypeNames = bodyTypeNameService.findAllByOrderByNameAsc();
        model.addAttribute("bodyTypeNames", bodyTypeNames);

        List<Generation> generations = generationService.findAllByOrderByIdAsc();
        model.addAttribute("generations", generations);

        List<Restyle> restyles = restyleService.findByGenerationIdOrderByNameAsc(user1.getTmp());
        model.addAttribute("restyles", restyles);

        Generation generation = generationService.findById(user1.getTmp());
        model.addAttribute("generation", generation);

        return "bodyTypeCreate";
    }

    @PostMapping("/bodyTypeCreate")
    public String bodyTypeCreate(@AuthenticationPrincipal User user,
                                 BodyType bodyType,
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

            bodyType.setPhoto(resultFilename);
        }

        User user1 = userService.findById(user.getId());

        bodyType.setGeneration(generationService.findById(user1.getTmp()));
        bodyTypeService.save(bodyType);

        return "redirect:/catalog/manufacturer/carmodel/generation/" + user1.getTmp();
    }

    @GetMapping("/catalog/manufacturer/carmodel/generation/delete/{id}")
    public String deleteBodyType(@AuthenticationPrincipal User user,
                                 @PathVariable("id") Long id) {
        bodyTypeService.deleteById(id);

        User user1 = userService.findById(user.getId());

        return "redirect:/catalog/manufacturer/carmodel/generation/" + user1.getTmp();
    }
}
