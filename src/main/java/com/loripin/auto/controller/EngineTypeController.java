package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.EngineType;
import com.loripin.auto.service.EngineTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EngineTypeController {
    private final
    EngineTypeService engineTypeService;

    public EngineTypeController(EngineTypeService engineTypeService) {
        this.engineTypeService = engineTypeService;
    }

    @GetMapping("/engineTypeList")
    public String engineTypeList(Model model) {
        List<EngineType> engineTypes = engineTypeService.findAllByOrderByNameAsc();
        model.addAttribute("engineTypes", engineTypes);
        return "engineTypeList";
    }

    @GetMapping("/engineTypeCreate")
    public String engineTypeCreateForm(EngineType engineType) {
        return "engineTypeCreate";
    }

    @PostMapping("/engineTypeCreate")
    public String engineTypeCreate(EngineType engineType) {
        engineTypeService.save(engineType);
        return "redirect:/engineTypeList";
    }

    @GetMapping("/engineTypeUpdate/{id}")
    public String engineTypeUpdateForm(@PathVariable Integer id, Model model){
        EngineType engineType = engineTypeService.findById(id);
        model.addAttribute("engineType", engineType);
        return "engineTypeUpdate";
    }

    @PostMapping("engineTypeUpdate")
    public String engineTypeUpdate(EngineType engineType) {
        engineTypeService.save(engineType);
        return "redirect:/engineTypeList";
    }

    @GetMapping("/engineTypeDelete/{id}")
    public String engineTypeDelete(@PathVariable("id") Integer id) {
        engineTypeService.deleteById(id);
        return "redirect:/engineTypeList";
    }
}
