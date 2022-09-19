package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.hashtag.HashTagService;
import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.post.postImg.PostImgService;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/posts")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final HashTagService hashTagService;

    private final PostImgService postImgService;
    //게시글 작성 폼으로 이동
    @GetMapping("")
    public String postForm(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/postForm";
    }

    //게시글 작성
    @PostMapping("")
    public String postCreate(PostFormDto postFormDto, @RequestParam("files") List<MultipartFile> files, Principal principal) {
        postService.createPost(postFormDto, files, principal);
        hashTagService.createHashTag(postFormDto.getTagContent(), principal);
        return "redirect:/posts/list";
    }

    //게시글 리스트로 이동

    @GetMapping({"/list/{page}","/list"})
    public String posts(@PathVariable(value = "page") Optional<Integer> page, Model model){
        Pageable pageable= PageRequest.of(page.isPresent()?page.get():0,6);
        Page<PostMainDto> posts=postService.getPostList(pageable);
        model.addAttribute("posts",posts);
        return "post/postList";
    }

    //게시글 상세 페이지로 이동
    @GetMapping("/{postId}")

    public String postDetail(@PathVariable("postId") int postId,Model model){
        PostDetailDto postDetailDto=postService.getPostDetail(postId);
        model.addAttribute("postDetailDto",postDetailDto);
        return "post/postDetail";
    }

    //게시글 수정 페이지로 이동
    @GetMapping("/{postId}/modify")
    public String postModifyForm(@PathVariable("postId") int postId, Principal principal,Model model) {
        Post post=postService.getPostById(postId);
        PostFormDto postFormDto=post.createPostFormDto();
        SiteUser siteUser=postService.getSiteUser(postId);
        if(!principal.getName().equals(siteUser.getUsername())){
            throw new RuntimeException("접속한 유저가 다르다!!");
        }
        model.addAttribute("postFormDto",postFormDto);
        return "post/postForm";
    }

    //게시글 수정
    @PostMapping("/{postId}/modify")
    public String postModify(Principal principal,PostFormUpdateDto postFormUpdateDto,@RequestParam("files") List<MultipartFile> files, @PathVariable("postId") int postId) {

      postService.modifyPost(postFormUpdateDto,files,postId,principal);
        return "redirect:/posts/list";
    }

    //게시글 삭제
    @GetMapping("/{postId}/delete")
    public String postDelete(@PathVariable("postId") int postId,Principal principal) {
        SiteUser siteUser=postService.getSiteUser(postId);
        Post post =postService.getPostById(postId);
        if(!principal.getName().equals(siteUser.getUsername())){
            throw new RuntimeException("접속한 유저가 다르다!!");
        }
        postImgService.deletePostImgList(post);
        postService.deletePost(postId);
        return "redirect:/posts/list";
    }

}
