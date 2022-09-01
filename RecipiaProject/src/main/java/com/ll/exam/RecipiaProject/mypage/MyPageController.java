package com.ll.exam.RecipiaProject.mypage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage") // MyPageController 안에 매핑되는 상위 page.
@Controller
@RequiredArgsConstructor // 생성자 주입.
public class MyPageController {


    @GetMapping("/home")
    public String mypageHome() {

        return "mypage_home";
    }


}