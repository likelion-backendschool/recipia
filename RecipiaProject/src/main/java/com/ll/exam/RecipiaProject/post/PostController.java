package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.comment.dto.CommentDto;
import com.ll.exam.RecipiaProject.comment.service.CommentService;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequestMapping("/posts")
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;
    private final HashTagService hashTagService;
    private final CommentService commentService;

    private final PostImgService postImgService;
    //게시글 작성 폼으로 이동
    @GetMapping("")
    public String postForm(Model model) {
        model.addAttribute("postFormDto", new PostFormDto());
        return "post/postForm";
    }

    //게시글 작성
    @PostMapping("")
    public String postCreate(PostFormDto postFormDto, @RequestParam("files") List<MultipartFile> files, Principal principal) throws IOException {
        postService.createPost(postFormDto, files, principal);
        return "redirect:/posts/list";
    }

    //게시글 리스트로 이동

    @GetMapping({"/list/{page}","/list"})
    public String posts(@PathVariable(value = "page") Optional<Integer> page,@RequestParam(value = "keyword")Optional<String> keyword,@RequestParam(value = "sort",defaultValue = "createdDate")String sort ,Model model){
        Sort addsort=Sort.by("post."+sort);
        Pageable pageable= PageRequest.of(page.isPresent()?page.get():0,6,addsort.descending());
        Page<PostMainDto> posts=null;
        String[] keywords=null;
        //검색어가 있는 경우와 없는경우 분기
        if(keyword.isPresent()){
            keywords=keyword.get().split(",");
            posts=postService.getPostListBykeyword(keywords,pageable);
        }else{
           posts=postService.getPostList(pageable);
        }
        model.addAttribute("posts",posts);
        model.addAttribute("keywords",keywords);
        return "post/postList";
    }

    //게시글 상세 페이지로 이동
    @GetMapping("/{postId}")
    public String postDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("postId") int postId, Model model){
        Cookie oldCookie=null;
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("visited")){
                    oldCookie=cookie;
                }
            }
        }

        if(oldCookie!=null){
            if(!oldCookie.getValue().contains("["+String.valueOf(postId)+"]")){
                postService.increaseView(postId);
                oldCookie.setValue(oldCookie.getValue()+"_["+postId+"]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60*60*24);
                response.addCookie(oldCookie);
            }
        }else{
            postService.increaseView(postId);
            Cookie newCookie=new Cookie("visited","["+postId+"]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60*60*24);
            response.addCookie(newCookie);
        }
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
    public String postModify(Principal principal,PostFormUpdateDto postFormUpdateDto,@RequestParam("files") List<MultipartFile> files, @PathVariable("postId") int postId) throws IOException {

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

    // 댓글 쓰기
    @PostMapping("/{postId}/reply")
    @ResponseBody
    public String postComment(@PathVariable("postId") int postId, @RequestBody CommentDto dto,Principal principal){
        commentService.createComment(postId,dto,principal);
        return "resp";
    }

    // 댓글 삭제

    @GetMapping("/{postId}/reply/{replyId}")
    @ResponseBody
    public String deleteComment(@PathVariable("replyId") long commentId, Principal principal){
        commentService.deleteComment(commentId,principal);
        return "resp";
    }
}
