package com.ll.exam.RecipiaProject.home;

import com.ll.exam.RecipiaProject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
        @GetMapping("")
        public String home() {
            return "home";

    }
}
