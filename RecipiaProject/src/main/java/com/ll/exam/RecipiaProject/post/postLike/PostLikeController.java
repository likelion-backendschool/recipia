package com.ll.exam.RecipiaProject.post.postLike;

import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import com.ll.exam.RecipiaProject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final UserRepository userRepository;

    @PostMapping("/like")
    @ResponseBody
    public int pushLikeButton(@RequestBody PostLikeJsonDto postLikeJsonDto){
        SiteUser siteUser=userRepository.findByUsername(postLikeJsonDto.getSiteUserName()).orElseThrow(EntityNotFoundException::new);
        int siteUserId=siteUser.getId();
        int postId=Integer.parseInt(postLikeJsonDto.getPostId());
        postLikeService.pushLikeButton(siteUserId,postId);
        int likes=postLikeService.getPostLikeNum(postId);
    return likes;
    }
}
