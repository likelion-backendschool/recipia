package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController
@RequiredArgsConstructor
public class MyPageRestController {

    private final MyPageService myPageService;

    /** 회원 수정 전 비밀번호 확인 **/
    @GetMapping("/check-pwd")
    public boolean checkPassword(@AuthenticationPrincipal SiteUser user,
                                 @RequestParam String checkPassword,
                                 Model model){

        log.info("checkPwd 진입");
        int User_id = user.getId();

        return myPageService.checkPassword(User_id, checkPassword);
    }
}
