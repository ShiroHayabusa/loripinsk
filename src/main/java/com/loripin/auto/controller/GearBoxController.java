package com.loripin.auto.controller;

import com.loripin.auto.model.GearBox;
import com.loripin.auto.model.GearBoxType;
import com.loripin.auto.service.GearBoxService;
import com.loripin.auto.service.GearBoxTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GearBoxController {
    private final
    GearBoxTypeService gearBoxTypeService;
    private final
    GearBoxService gearBoxService;

    public GearBoxController(GearBoxService gearBoxService,
                             GearBoxTypeService gearBoxTypeService) {
        this.gearBoxService = gearBoxService;
        this.gearBoxTypeService = gearBoxTypeService;
    }

    @GetMapping("/gearBoxList")
    public String gearBoxList(Model model) {
        List<GearBox> gearBoxes = gearBoxService.findAllByOrderByIdAsc();
        model.addAttribute("gearBoxes", gearBoxes);
        return "gearBoxList";
    }

    @GetMapping("/gearBoxCreate")
    public String gearBoxCreateForm(GearBox gearBox,
                                    Model model) {
        List<GearBoxType> gearBoxTypes = gearBoxTypeService.findAllByOrderByNameAsc();
        model.addAttribute("gearBoxTypes", gearBoxTypes);
        return "gearBoxCreate";
    }

    @PostMapping("/gearBoxCreate")
    public String gearBoxCreate(GearBox gearBox) {
        gearBoxService.save(gearBox);
        return "redirect:/gearBoxList";
    }
}
