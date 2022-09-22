package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/mypage") // 여기에 적으면 MyPageController URL 상위
@Controller
@RequiredArgsConstructor // 생성자 주입.
public class MyPageController {

    private final UserRepository userRepository;

    private final MyPageService myPageService;

    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public String mypageHome(Model model, MyPageDto myPageDto, Principal principal) {

        if (principal != null) {
            System.out.println("타입정보 : " + principal.getClass());
            System.out.println("ID정보 : " + principal.getName());
        }

        SiteUser siteUser = myPageService.getUser(principal.getName());
        MyPageDto _myPageDto = MyPageDto.createMyPageDto(siteUser);
        model.addAttribute("myPageDto", _myPageDto);

        return "mypage/mypage_home";
    }

    @GetMapping("/myposts")
    @PreAuthorize("isAuthenticated()")
    public String myPosts(Principal principal) {
        return "mypage/mypage_myPosts";
    }

    @GetMapping("/bookmark")
    @PreAuthorize("isAuthenticated()")
    public String myPageBookmark(Principal principal) {
        return "mypage/mypage_bookmark";
    }

    @GetMapping("/allergy")
    @PreAuthorize("isAuthenticated()")
    public String myPageAllergyFilter(Principal principal) {
        return "mypage/mypage_allergyFilter";
    }

    @GetMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String showModify() {
        return "mypage/mypage_userModify";
    }

    @PostMapping("/modify")
    @PreAuthorize("isAuthenticated()")
    public String modify(@AuthenticationPrincipal SiteUser siteUser, String password, String email, String nickname, String gender) {
        SiteUser siteUser1 = myPageService.getUser(siteUser.getUsername());

        myPageService.modify(siteUser1, password, email, nickname, gender);

        return "redirect:/mypage";
    }
    @GetMapping("/withdraw")
    @PreAuthorize("isAuthenticated()")
    public String userWithdraw(Principal principal) {
        SiteUser siteUser = myPageService.getUser(principal.getName());


        myPageService.delete(siteUser);
        return "redirect:/user/logout";
    }


}