package com.loripin.auto.controller;

import com.loripin.auto.model.User;
import com.loripin.auto.model.dto.SpotDto;
import com.loripin.auto.service.SpotService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    private final
    SpotService spotService;

    public AdminController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/adminPage")
    public String adminPage(@AuthenticationPrincipal User user, Model model) {
        List<SpotDto> spots = spotService.findAllByOrderByIdDesc(user);
        model.addAttribute("spots", spots);
        return "adminPage";
    }
}
