package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.PostFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RequestMapping("/hashtag")
@RequiredArgsConstructor
@Controller
public class HashTagController {
    private final HashTagService hashTagService;
    private final HashTagRepository hashTagRepository;


    @GetMapping("")
    public String hashTagForm(Model model) {
        model.addAttribute("hashTagFormDto", new HashTagFormDto());
        return "hashtag/hashtagForm";
    }

    @PostMapping("")
    public String hashTagCreate(HashTagFormDto hashTagFormDto,Principal principal){
        hashTagService.createHashTag(hashTagFormDto,principal);
        return "redirect:/hashtag/list";
    }

    @GetMapping("/list")
    public String list(Model model){
        List<HashTag> hashTagList = this.hashTagRepository.findAll();
        model.addAttribute("hashtagList", hashTagList);
        return"hashtag/hashtagList";
    }

}
