package com.ll.exam.RecipiaProject.hashtag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class HashTagController {
    private final HashTagService hashTagService;
    private final HashTagRepository hashTagRepository;

    @RequestMapping("/hashtag")
    @GetMapping("")
    public String hashTagForm(Model model) {
        return "hashtag/hashtagForm";
    }
}
