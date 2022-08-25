package com.ll.exam.RecipiaProject.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyPageController {

    @RequestMapping("/mypage") // URL 매핑
    @ResponseBody // URL 요청에 대한 응답으로 문자열 리턴
    public String myPage() {
        return "MY PAGE 입니다.";
    }
}
