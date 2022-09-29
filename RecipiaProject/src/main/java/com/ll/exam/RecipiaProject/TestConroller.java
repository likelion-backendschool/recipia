package com.ll.exam.RecipiaProject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
@Controller
public class TestConroller {

    @GetMapping("/")
    public String test22(){
        return "test2";
    }
}
