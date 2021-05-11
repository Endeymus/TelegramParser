package com.endeymus.parser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mark Shamray
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Старница авторизации");
        return "main/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "Старница регистрации");
        return "main/registration";
    }

    @PostMapping("/registration")
    public String register() {

        return "redirect:login";
    }
}
