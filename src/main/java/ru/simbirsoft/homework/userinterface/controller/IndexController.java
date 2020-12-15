package ru.simbirsoft.homework.userinterface.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.simbirsoft.homework.aop.annotations.LogTime;
import ru.simbirsoft.homework.userinterface.service.UserService;

@LogTime
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(!auth.getName().equals("anonymousUser")) {
            model.addAttribute("name", userService.loadByUsername(auth.getName()));
            model.addAttribute("role",auth.getAuthorities());
        }else{
            model.addAttribute("name", auth.getName());
        }
        return "index";
    }
    @GetMapping("/login")
    public String loginGet(Model model) {
        return "login";
    }
    @GetMapping("/ui/authors")
    public String authors(Model model){return "authors";}
    @GetMapping("/ui/genres")
    public String genres(Model model){return "genres";}
    @GetMapping("/ui/library")
    public String library(Model model){return "library";}
    @GetMapping("/ui/books")
    public String books(Model model){
        return "books";
    }
    @GetMapping("/ui/persons")
    public String persons(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        return "persons";
    }
}
