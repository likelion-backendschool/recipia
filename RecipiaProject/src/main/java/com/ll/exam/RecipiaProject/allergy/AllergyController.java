package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.hashtag.HashTagFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/allergy")
@RequiredArgsConstructor
@Controller
public class AllergyController {
    private final AllergyRepository allergyRepository;
    private final AllergyService allergyService;
    @GetMapping("")
    public String allergyForm(Model model) {
        model.addAttribute("allergyFormDto", new AllergyFormDto());
        return "allergy/allergyForm";
    }

    @PostMapping("")
    public String allergyCreate(String allergyContent, Principal principal) {
        allergyService.createAllergy(allergyContent, principal);
        return "redirect:/allergy/allergyList";
    }

    @GetMapping("/allergylist")
    public String list(Model model){
        model.addAttribute("allergyList", allergyService.getAllergyList());
        return "allergy/allergyList";
    }

}
