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

    //게시글 작성 폼으로 이동
    @GetMapping("")
    public String postForm(Model model){
        model.addAttribute("postFormDto",new PostFormDto());
        return "post/postForm";
    }

    //게시글 작성
    @PostMapping("")
    public String postCreate(){
        return "redirect:/posts/list";
    }

    //게시글 리스트로 이동
    @GetMapping("/list")
    public String posts(Model model){
        List<Post> posts=postService.getPostList();
        model.addAttribute("posts",posts);
        return "post/postList";
    }

    //게시글 상세 페이지로 이동
    @GetMapping("/{postId}")
    public String postDetail(@PathVariable("postId") int postId,Model model){
        return "post/postDetail";
    }

    //게시글 수정 페이지로 이동
    @GetMapping("/{postId}/modify")
    public String postModifyForm(@PathVariable("postId") int postId,Model model){
        return "post/postModifyForm";
    }

    //게시글 수정
    @PatchMapping("/{postId}/modify")
    public String postModify(@PathVariable("postId") int postId){
        return "redirect:/posts/list";
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public String postDelete(@PathVariable("postId") int postId){
        return "redirect:/posts/list";
    }

}
