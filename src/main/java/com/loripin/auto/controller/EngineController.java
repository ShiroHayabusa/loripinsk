package com.loripin.auto.controller;

import com.loripin.auto.model.*;
import com.loripin.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class EngineController {
    private final
    ModificationService modificationService;
    private final
    ManufacturerService manufacturerService;
    private final
    FuelService fuelService;
    private final
    EngineTypeService engineTypeService;
    private final
    EngineService engineService;

    public EngineController(EngineService engineService,
                            EngineTypeService engineTypeService,
                            FuelService fuelService,
                            ManufacturerService manufacturerService, ModificationService modificationService) {
        this.engineService = engineService;
        this.engineTypeService = engineTypeService;
        this.fuelService = fuelService;
        this.manufacturerService = manufacturerService;
        this.modificationService = modificationService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/engineList")
    public String engineList(Model model) {
        List<Engine> engines = engineService.findAllByOrderByNameAsc();
        model.addAttribute("engines", engines);
        return "engineList";
    }

    @GetMapping("/engineCreate")
    public String engineCreateForm(Engine engine,
                                   Model model) {
        List<EngineType> engineTypes = engineTypeService.findAllByOrderByNameAsc();
        model.addAttribute("engineTypes", engineTypes);
        List<Fuel> fuels = fuelService.findAllByOrderByIdAsc();
        model.addAttribute("fuels", fuels);
        List<Manufacturer> manufacturers = manufacturerService.findAllByOrderByNameAsc();
        model.addAttribute("manufacturers", manufacturers);
        return "engineCreate";
    }

    @PostMapping("/engineCreate")
    public String engineCreate(Engine engine,
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

            engine.setPhoto(resultFilename);
        }
        engineService.save(engine);
        return "redirect:/engineList";
    }

    @GetMapping("/engineUpdate/{id}")
    public String engineUpdateForm(@PathVariable Long id,
                                   Model model){
        Engine engine = engineService.findById(id);
        model.addAttribute("engine", engine);
        existingPhoto = engine.getPhoto();
        List<EngineType> engineTypes = engineTypeService.findAllByOrderByNameAsc();
        model.addAttribute("engineTypes", engineTypes);
        List<Fuel> fuels = fuelService.findAllByOrderByIdAsc();
        model.addAttribute("fuels", fuels);
        List<Manufacturer> manufacturers = manufacturerService.findAllByOrderByNameAsc();
        model.addAttribute("manufacturers", manufacturers);
        return "engineUpdate";
    }

    @PostMapping("engineUpdate")
    public String engineUpdate(Engine engine,
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
                engine.setPhoto(existingPhoto);
            } else {
                engine.setPhoto(resultFilename);
            }
        }
        engineService.save(engine);
        return "redirect:/engineList";
    }

    @GetMapping("/engineDelete/{id}")
    public String engineDelete(@PathVariable("id") Long id) {
        engineService.deleteById(id);
        return "redirect:/engineList";
    }

    @GetMapping("/engineOpen/{id}")
    public String engineOpen(@PathVariable Long id, Model model) {
        Engine engine = engineService.findById(id);
        model.addAttribute("engine", engine);

        List<Modification> modifications = modificationService.findByEngineIdOrderByIdDesc(id);
        model.addAttribute("modifications", modifications);
        return "engineOpen";
    }
}
