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
import java.util.List;
import java.util.UUID;

import static com.loripin.auto.controller.ModificationController.existingPhoto;

@Controller
public class RestyleController {
    private final
    FileStorageImpl fileStorageImpl;
    private final
    FileUpload fileUpload;
    private final
    GenerationService generationService;
    private final
    UserService userService;
    private final
    SpecService specService;
    private final
    BodyTypeNameService bodyTypeNameService;
    private final
    BodyTypeService bodyTypeService;
    private final
    ModificationService modificationService;
    private final
    RestyleService restyleService;

    public RestyleController(RestyleService restyleService,
                             ModificationService modificationService,
                             BodyTypeService bodyTypeService,
                             BodyTypeNameService bodyTypeNameService,
                             SpecService specService, UserService userService, GenerationService generationService, FileUpload fileUpload, FileStorageImpl fileStorageImpl) {
        this.restyleService = restyleService;
        this.modificationService = modificationService;
        this.bodyTypeService = bodyTypeService;
        this.bodyTypeNameService = bodyTypeNameService;
        this.specService = specService;
        this.userService = userService;
        this.generationService = generationService;
        this.fileUpload = fileUpload;
        this.fileStorageImpl = fileStorageImpl;
    }

    @Value("${upload.path}")
    private String uploadPath;

    public static String manufacturer1;
    public static String carmodel1;

    @GetMapping("/restyleCreate")
    public String createRestyleForm(@AuthenticationPrincipal User user,
                                    Restyle restyle,
                                    Model model) {
        User user1 = userService.findById(user.getId());
        Generation generation = generationService.findById(user1.getTmp());
        model.addAttribute("generation", generation);
        return "restyleCreate";
    }

    @PostMapping("/restyleCreate")
    public String createRestyle(@AuthenticationPrincipal User user,
                                Restyle restyle) {
        User user1 = userService.findById(user.getId());
        restyle.setGeneration(generationService.findById(user1.getTmp()));
        restyleService.saveRestyle(restyle);
        return "redirect:/catalog/manufacturer/carmodel/generation/" + user1.getTmp();
    }

    @GetMapping("restyleUpdate/{id}")
    public String updateRestyleForm(@AuthenticationPrincipal User user,
                                    @PathVariable("id") Long id,
                                    Model model) {
        List<BodyTypeName> bodyTypeNames = bodyTypeNameService.findAllByOrderByIdAsc();
        model.addAttribute("bodyTypeNames", bodyTypeNames);
        BodyType bodyType = bodyTypeService.findById(id);
        model.addAttribute("bodyType", bodyType);
        existingPhoto = bodyType.getCarPhoto();
        user.setTmp(bodyType.getId());
        userService.save(user);
        List<Spec> specs = specService.findAllByOrderByName();
        model.addAttribute("specs", specs);
        return "restyleUpdate";
    }

    @PostMapping("/restyleUpdate")
    public String updateRestyle(@AuthenticationPrincipal User user,
                                BodyType bodyType,
                                @RequestParam("file") MultipartFile file
    ) throws IOException {

        File uploadDir1 = new File(uploadPath + "/" + bodyType.getGeneration().getManufacturer().getName());
        fileStorageImpl.uploadDir2 = new File(uploadPath + "/" + bodyType.getGeneration().getManufacturer().getName() +
                "/" + bodyType.getGeneration().getCarmodel().getName());

        if (!uploadDir1.exists()) {
            uploadDir1.mkdir();
        }

        if (!fileStorageImpl.uploadDir2.exists()) {
            fileStorageImpl.uploadDir2.mkdir();
        }

        if (file.isEmpty()) {
            bodyType.setCarPhoto(existingPhoto);
        } else {
            bodyType.setCarPhoto(fileUpload.fileUpload(file));
        }

        bodyTypeService.save(bodyType);
        User user1 = userService.findById(user.getId());
        return "redirect:/catalog/manufacturer/carmodel/generation/restyle/" + user1.getTmp();
    }

    @GetMapping("/catalog/manufacturer/carmodel/generation/restyle/{id}")
    public String openRestyle(@AuthenticationPrincipal User user,
                              @PathVariable("id") Long id,
                              Model model) {
        List<Modification> modifications = modificationService.findByBodyTypeIdOrderByName(id);
        model.addAttribute("modifications", modifications);
        int counter = 0;
        for (Modification modification : modifications) {
            if (modification.getTuner() != null) {
                counter = counter + 1;
            }
        }
        model.addAttribute("counter", counter);

        BodyType bodyType = bodyTypeService.findById(id);
        model.addAttribute("bodyType", bodyType);

        manufacturer1 = bodyType.getGeneration().getManufacturer().getName();
        carmodel1 = bodyType.getGeneration().getCarmodel().getName();

        if (user != null) {
            user.setTmp(bodyType.getId());
            userService.save(user);
        }

        return "restyleOpen";
    }
}
