package com.ll.exam.RecipiaProject.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    @GetMapping("")
    public String home() {
        return "home";

    }

    @PostMapping("")
    public String home2() {
        return "redirect:/posts/list";
    }
}