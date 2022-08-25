package com.ll.exam.RecipiaProject.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class myPageController {

    @RequestMapping("/mypage")
    @ResponseBody
    public String myPage() {
        return "MY PAGE 입니다.";
    }
}
