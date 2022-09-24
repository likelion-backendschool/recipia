package com.ll.exam.RecipiaProject.post.postLike;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;
    @GetMapping("/like")
    @ResponseBody
    public String pushLikeButton(@RequestParam("siteUserId") Integer siteUserId, @RequestParam("postId") Integer postId){
        postLikeService.pushLikeButton(siteUserId,postId);
    return "Success";
    }
}
