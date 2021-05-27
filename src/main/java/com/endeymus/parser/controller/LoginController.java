package com.endeymus.parser.controller;

import com.endeymus.parser.entity.Roles;
import com.endeymus.parser.entity.User;
import com.endeymus.parser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Mark Shamray
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Старница авторизации");
        return "main/login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "Старница регистрации");
        model.addAttribute("user", new User());
        return "main/registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user")User user) {
        User find = userService.findByLogin(user.getLogin());
        if (find != null) {
            return "redirect:registration?error='Данный логин уже занят'";
        }
        Set<Roles> roles = new HashSet<>();
        roles.add(new Roles(2L, "ROLE_USER"));
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setRoles(roles);
        user.setPassword(encode);
        user.generateHash(); // генерирование hashKey
        userService.save(user);
        return "redirect:login";
    }
}
