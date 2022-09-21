package com.ll.exam.RecipiaProject.comment.controller;

import com.ll.exam.RecipiaProject.comment.dto.CommentDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/comment")
public class CommentController {

    @GetMapping("")
    public String showComment(){
        return "comment/comment";
    }

    @PostMapping("/dataSend")
    public String postComment(Model model, CommentDto dto){
        model.addAttribute("msg",dto.getResult() + "/ this is the value sent by the server ");
        return "comment/comment :: #resultDiv";
    }

}
