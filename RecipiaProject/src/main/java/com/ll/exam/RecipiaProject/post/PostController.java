package com.ll.exam.RecipiaProject.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/posts")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public String postForm(Model model){

        return "post/postForm";
    }

    @PostMapping("")
    public String postCreate(){
        return "redirect:/posts/list";
    }

    @GetMapping("/list")
    public String posts(Model model){
        List<Post> posts=postService.getPostList();
        model.addAttribute("posts",posts);
        return "post/postList";
    }

    @GetMapping("/{postId}")
    public String postDetail(@PathVariable("postId") int postId,Model model){
        return "post/postDetail";
    }

    @GetMapping("/{postId}/modify")
    public String postModifyForm(@PathVariable("postId") int postId,Model model){
        return "post/postModifyForm";
    }
    @PatchMapping("/{postId}/modify")
    public String postModify(@PathVariable("postId") int postId){
        return "redirect:/posts/list";
    }
    @DeleteMapping("/{postId}")
    public String postDelete(@PathVariable("postId") int postId){
        return "redirect:/posts/list";
    }

}
