package ru.simbirsoft.homework.userinterface.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.simbirsoft.homework.userinterface.model.User;
import ru.simbirsoft.homework.userinterface.service.UserService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, Model model) {
        if (!userService.saveUser(user)){
            model.addAttribute("error", "Пользователь с таким именем уже существует");
            return "registration";
        }
        return "redirect:/";
    }
}