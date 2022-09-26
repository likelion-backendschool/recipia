package com.ll.exam.RecipiaProject.post.postLike;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.post.PostRepository;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import com.ll.exam.RecipiaProject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostLikeController {
    private final PostLikeService postLikeService;
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    @PostMapping("/like")
    @ResponseBody
    public int pushLikeButton(@RequestBody PostLikeJsonDto postLikeJsonDto){
        SiteUser siteUser=userRepository.findByUsername(postLikeJsonDto.getSiteUserName()).orElseThrow(EntityNotFoundException::new);
        int postId=Integer.parseInt(postLikeJsonDto.getPostId());
        int siteUserId=siteUser.getId();
        Post post=postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        postLikeService.pushLikeButton(siteUserId,postId);
        int likes=postLikeService.getPostLikeNum(postId);
        post.setLikes(likes);
        return likes;
    }
}
