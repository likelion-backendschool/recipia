package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgService;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;
@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final PostImgService postImgService;
    public  List<Post> getPostList(){
        return postRepository.findAll();
    }

    public void createPost(PostFormDto postFormDto,List<MultipartFile> files,Principal principal) {
        //로그인 기능 추가시 여기에 principal 로 SiteUser 불러오기
        SiteUser user=userRepository.findByUsername("user1");
        Post post=postFormDto.createPost(user);
        postRepository.save(post);
        for(int i=0;i<files.size();i++){
            PostImg postImg=new PostImg();
            postImg.setPost(post);

            if(i==0){
                postImg.setThumbnailYn(true);
            }else {
                postImg.setThumbnailYn(false);
            }
            postImgService.createPostImg(postImg,files.get(i));
        }
    }
}
