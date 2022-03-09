package com.loripin.auto.controller;

import com.loripin.auto.model.Body;
import com.loripin.auto.model.Country;
import com.loripin.auto.model.Manufacturer;
import com.loripin.auto.service.CountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CountryController {
    private final
    CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countryCreate")
    public String countryCreateForm(Country country) {
        return "countryCreate";
    }

    @PostMapping("/countryCreate")
    public String countryCreate(Country country) {
        countryService.save(country);
        return "redirect:/countryList";
    }

    @GetMapping("/countryList")
    public String countryList(Model model) {
        List<Country> countries = countryService.findAllByOrderByName();
        model.addAttribute("countries", countries);
        return "countryList";
    }

    @GetMapping("/countryUpdate/{id}")
    public String countryUpdateForm(@PathVariable Long id, Model model){
        Country country = countryService.findById(id);
        model.addAttribute("country", country);
        return "countryUpdate";
    }

    @PostMapping("countryUpdate")
    public String countryUpdate(Country country) {
        countryService.save(country);
        return "redirect:/countryList";
    }

    @GetMapping("/countryDelete/{id}")
    public String countryDelete(@PathVariable("id") Long id) {
        countryService.deleteById(id);
        return "redirect:/countryList";
    }

}
