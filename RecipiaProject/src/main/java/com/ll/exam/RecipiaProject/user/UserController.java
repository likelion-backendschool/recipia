package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUpForm(UserFormDto userFormDto){
        return "signup_form";
    }

    @PostMapping("/sign-up")
    public String signUpFormPost(@Valid UserFormDto userFormDto, BindingResult bindingResult){
        // 만일 오류가 있다면 다시 회원가입 입력 폼으로
        if (bindingResult.hasErrors()){
            return "signup_form";
        }
        if (!userFormDto.getPassword1().equals(userFormDto.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect"
                    ,"비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }
        userService.create(userFormDto.getUsername(), userFormDto.getPassword1(), userFormDto.getEmail());
        return "redirect:/user/home";
    }
}
