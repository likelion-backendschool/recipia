package com.ll.exam.RecipiaProject.user;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoArgsConstructor
@RequestMapping("/user")
public class UserController {

    @GetMapping("/sign-up")
    public String signUpForm(){
        return "signup_form";
    }
}
