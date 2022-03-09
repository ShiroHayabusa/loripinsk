package com.loripin.auto.controller;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Fuel;
import com.loripin.auto.service.FuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FuelController {
    private final
    FuelService fuelService;

    public FuelController(FuelService fuelService) {
        this.fuelService = fuelService;
    }

    @GetMapping("/fuelList")
    public String fuelList(Model model) {
        List<Fuel> fuels = fuelService.findAllByOrderByIdAsc();
        model.addAttribute("fuels", fuels);
        return "fuelList";
    }

    @GetMapping("/fuelCreate")
    public String fuelCreateForm(Fuel fuel) {
        return "fuelCreate";
    }

    @PostMapping("/fuelCreate")
    public String fuelCreate(Fuel fuel) {
        fuelService.save(fuel);
        return "redirect:/fuelList";
    }

    @GetMapping("/fuelUpdate/{id}")
    public String fuelUpdateForm(@PathVariable Integer id, Model model){
        Fuel fuel = fuelService.findById(id);
        model.addAttribute("fuel", fuel);
        return "fuelUpdate";
    }

    @PostMapping("fuelUpdate")
    public String fuelUpdate(Fuel fuel) {
        fuelService.save(fuel);
        return "redirect:/fuelList";
    }

    @GetMapping("/fuelDelete/{id}")
    public String fuelDelete(@PathVariable("id") Integer id) {
        fuelService.deleteById(id);
        return "redirect:/fuelList";
    }
}
