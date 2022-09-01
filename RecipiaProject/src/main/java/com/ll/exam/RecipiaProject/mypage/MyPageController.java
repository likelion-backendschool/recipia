package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;

@RequestMapping("/mypage") // 여기에 적으면 MyPageController URL 상위
@Controller
@RequiredArgsConstructor // 생성자 주입.
public class MyPageController {

    private final UserRepository userRepository;

    @GetMapping("/home")
    public String mypageHome() {
        //예외처리를 위한 옵셔널 다시 체킹해야함
        SiteUser siteUser = userRepository.findByUsername("user1").orElseThrow(() ->new EntityNotFoundException());
        MyPageDto myPageDto = MyPageDto.createMyPageDto(siteUser);

        return "mypage/mypage_home";
    }


}