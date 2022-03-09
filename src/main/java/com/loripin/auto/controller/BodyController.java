package com.loripin.auto.controller;

import com.loripin.auto.model.Body;
import com.loripin.auto.model.Manufacturer;
import com.loripin.auto.service.BodyService;
import com.loripin.auto.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BodyController {
    private final
    ManufacturerService manufacturerService;
    private final
    BodyService bodyService;

    public BodyController(BodyService bodyService, ManufacturerService manufacturerService) {
        this.bodyService = bodyService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("/bodyList")
    public String bodyList(Model model) {
        List<Body> bodies = bodyService.findAllByOrderByNameAsc();
        model.addAttribute("bodies", bodies);
        return "bodyList";
    }

    @GetMapping("/bodyCreate")
    public String bodyCreateForm(Body body, Model model) {
        List<Manufacturer> manufacturers = manufacturerService.findAllByOrderByNameAsc();
        model.addAttribute("manufacturers", manufacturers);
        return "bodyCreate";
    }

    @PostMapping("/bodyCreate")
    public String bodyCreate(Body body) {
        bodyService.save(body);
        return "redirect:/bodyList";
    }

    @GetMapping("/bodyUpdate/{id}")
    public String bodyUpdateForm(@PathVariable Long id, Model model){
        Body body = bodyService.findById(id);
        model.addAttribute("body", body);
        List<Manufacturer> manufacturers = manufacturerService.findAllByOrderByNameAsc();
        model.addAttribute("manufacturers", manufacturers);
        return "bodyUpdate";
    }

    @PostMapping("bodyUpdate")
    public String bodyUpdate(Body body) {
        bodyService.save(body);
        return "redirect:/bodyList";
    }

    @GetMapping("/bodyDelete/{id}")
    public String bodyDelete(@PathVariable("id") Long id) {
        bodyService.deleteById(id);
        return "redirect:/bodyList";
    }
}
