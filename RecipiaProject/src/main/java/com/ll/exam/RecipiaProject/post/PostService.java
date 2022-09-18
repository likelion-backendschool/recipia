package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgRepository;
import com.ll.exam.RecipiaProject.post.postImg.PostImgService;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
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
    private final PostImgRepository postImgRepository;


    public Page<PostMainDto> getPostList(Pageable pageable){
        return postRepository.getPostList(pageable);

    }

    public void createPost(PostFormDto postFormDto, List<MultipartFile> files, Principal principal) {
        //로그인 기능 추가시 여기에 principal 로 SiteUser 불러오기

        SiteUser user=userRepository.findByUsername(principal.getName()).orElseThrow(()->new EntityNotFoundException());
        Post post=postFormDto.createPost(user);

        postRepository.save(post);
        for (int i = 0; i < files.size(); i++) {
            PostImg postImg = new PostImg();
            postImg.setPost(post);

            if (i == 0) {
                postImg.setThumbnailYn(true);
            } else {
                postImg.setThumbnailYn(false);
            }
            postImgService.createPostImg(postImg, files.get(i));
        }
    }

    public PostDetailDto getPostDetail(int postId) {
        Post post=postRepository.getPostDetail(postId);
        return post.createPostDetailDto();
    }

    public SiteUser getSiteUser(int postId) {
        return postRepository.getSiteUser(postId);
    }


    public Post getPostById(int postId) {
        return postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException());
    }

    public void modifyPost(PostFormUpdateDto postFormUpdateDto, List<MultipartFile> files,int postId,Principal principal) {
        Post post=postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException());
        post.setTitle(postFormUpdateDto.getTitle());
        post.setContent(postFormUpdateDto.getContent());
            for (int i = 0; i < files.size(); i++) {
                int  postImgId;

               try{
                   postImgId=Integer.parseInt(postFormUpdateDto.getPostImgIds().get(i));
               }catch (IndexOutOfBoundsException e){
                   PostImg postImg = new PostImg();
                   postImg.setPost(post);
                   postImg.setThumbnailYn(false);
                   postImgService.createPostImg(postImg, files.get(i));
                   continue;
               }

                String nowOriImgName=postImgRepository.findById(postImgId).get().getOriImgName();
                String newOriImgName=postFormUpdateDto.getImgNames().get(i);
              if(newOriImgName.equals(nowOriImgName)){
                  continue;
              }
                //다른 파일인경우
             if(!newOriImgName.equals(nowOriImgName)&&!newOriImgName.equals("")){
                 System.out.println("idx:"+i);
                 System.out.println("newOriImgName:"+newOriImgName);
                 System.out.println("nowOriImgName:"+nowOriImgName);
                 postImgService.deletePostImg(postImgId);
                 PostImg postImg = new PostImg();
                 postImg.setPost(post);

                 if (i ==0) {
                     postImg.setThumbnailYn(true);
                 } else {
                     postImg.setThumbnailYn(false);
                 }
                 postImgService.createPostImg(postImg, files.get(i));
             }
             }

            if(files.size()<postFormUpdateDto.getPostImgIds().size()){
               for(int j=files.size();j< postFormUpdateDto.getPostImgIds().size();j++){
                   postImgService.deletePostImg(Integer.parseInt(postFormUpdateDto.getPostImgIds().get(j)));
               }
            }
        }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
}