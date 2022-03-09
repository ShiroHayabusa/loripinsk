package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.GearBoxType;
import com.loripin.auto.service.GearBoxTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class GearBoxTypeController {
    private final
    GearBoxTypeService gearBoxTypeService;

    public GearBoxTypeController(GearBoxTypeService gearBoxTypeService) {
        this.gearBoxTypeService = gearBoxTypeService;
    }

    @GetMapping("/gearBoxTypeList")
    public String gearBoxTypeList(Model model) {
        List<GearBoxType> gearBoxTypes = gearBoxTypeService.findAllByOrderByNameAsc();
        model.addAttribute("gearBoxTypes", gearBoxTypes);
        return "gearBoxTypeList";
    }

    @GetMapping("/gearBoxTypeCreate")
    public String gearBoxTypeCreateForm(GearBoxType gearBoxType) {
        return "gearBoxTypeCreate";
    }

    @PostMapping("/gearBoxTypeCreate")
    public String gearBoxTypeCreate(GearBoxType gearBoxType) {
        gearBoxTypeService.save(gearBoxType);
        return "redirect:/gearBoxTypeList";
    }

    @GetMapping("/gearBoxTypeUpdate/{id}")
    public String gearBoxTypeUpdateForm(@PathVariable Long id, Model model){
        GearBoxType gearBoxType = gearBoxTypeService.findById(id);
        model.addAttribute("gearBoxType", gearBoxType);
        return "gearBoxTypeUpdate";
    }

    @PostMapping("gearBoxTypeUpdate")
    public String gearBoxTypeUpdate(GearBoxType gearBoxType) {
        gearBoxTypeService.save(gearBoxType);
        return "redirect:/gearBoxTypeList";
    }

    @GetMapping("/gearBoxTypeDelete/{id}")
    public String gearBoxTypeDelete(@PathVariable("id") Long id) {
        gearBoxTypeService.deleteById(id);
        return "redirect:/gearBoxTypeList";
    }
}
