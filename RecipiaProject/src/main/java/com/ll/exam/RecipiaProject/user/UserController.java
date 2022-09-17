package com.ll.exam.RecipiaProject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "user/signup_form";
    }

    @PostMapping("/sign-up")
    public String signUpFormPost(@Valid UserFormDto userFormDto, BindingResult bindingResult){
        // 만일 오류가 있다면 다시 회원가입 입력 폼으로
        if (bindingResult.hasErrors()){
            return "user/signup_form";
        }
        if (!userFormDto.getPassword1().equals(userFormDto.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect"
                    ,"비밀번호가 일치하지 않습니다.");
            return "user/signup_form";
        }
        try{
            userService.create(userFormDto.getUsername(), userFormDto.getPassword1(), userFormDto.getEmail());
        }catch (UsernameDuplicatedException e){
            bindingResult.reject("usernameDuplicatedError", e.getMessage());
            return "user/signup_form";
        }catch (EmailDuplicatedException e){
            bindingResult.reject("emailDuplicatedError", e.getMessage());
            return "user/signup_form";
        }
        return "redirect:/user/home";
    }

    @GetMapping("/login")
    public String userLogin(){
        return "user/login_form";
    }

    @GetMapping("/find-password")
    public String findPassword(){
        return "user/find_pw_form";
    }

    @PostMapping("/find-password")
    public String findPasswordPost(@RequestParam("email") String email){
        if (!userService.checkEmail(email)){
            return "user/find_pw_form";
        }
        return "user/certification_num";
    }
}
