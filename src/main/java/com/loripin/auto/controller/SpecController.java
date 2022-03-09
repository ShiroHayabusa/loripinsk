package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Spec;
import com.loripin.auto.service.CountryService;
import com.loripin.auto.service.SpecService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SpecController {
    private final
    CountryService countryService;
    private final
    SpecService specService;

    public SpecController(SpecService specService, CountryService countryService) {
        this.specService = specService;
        this.countryService = countryService;
    }

    @GetMapping("/specList")
    public String listSpec(Model model) {
        model.addAttribute("specs", specService.findAllByOrderByName());
        return "specList";
    }

    @GetMapping("/specCreate")
    public String createSpecForm(Spec spec, Model model){
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "specCreate";
    }

    @PostMapping("/specCreate")
    public String createSpec(Spec spec) {
        specService.save(spec);
        return "redirect:/specList";
    }

    @GetMapping("/specUpdate/{id}")
    public String updateSpecForm(@PathVariable Long id, Model model){
        Spec spec = specService.findById(id);
        model.addAttribute("spec", spec);
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "specUpdate";
    }

    @PostMapping("specUpdate")
    public String updateSpec(Spec spec){
        specService.save(spec);
        return "redirect:/specList";
    }
}
