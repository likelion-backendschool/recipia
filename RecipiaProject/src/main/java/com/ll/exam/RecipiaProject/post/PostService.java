package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.aws.AwsS3Service;
import com.ll.exam.RecipiaProject.hashtag.HashTagRepository;
import com.ll.exam.RecipiaProject.hashtag.HashTagService;
import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgRepository;
import com.ll.exam.RecipiaProject.post.postLike.PostLikeRepository;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashTagService hashTagService;
    private final AwsS3Service postImgService;
    private final PostImgRepository postImgRepository;

    private final HashTagRepository hashTagRepository;

    private final PostLikeRepository postLikeRepository;
    public Page<PostMainDto> getPostList(Pageable pageable){
        Page<Post> posts = postRepository.getPostList(pageable);
        Page<PostMainDto> map = posts.map(post ->
                PostMainDto.builder()
                        .title(post.getTitle())
                        .id(post.getId())
                        .hashTagContentList(post.getHashTagList().stream().map(hashTag ->
                                hashTag.getTagContent()).collect(Collectors.toList()))
                        .imgUrl(post.getPostImgList().get(0).getImgUrl())
                        .score(post.getScore())
                        .likes(post.getLikes())
                        .likedSiteUserNameList(postLikeRepository.getPostLikedSiteUserName(post))
                        .views(post.getViews())
                        .build()
        );
        return map;
    }

    public Page<PostMainDto> getPostListBykeyword(String[] keywords,Pageable pageable) {
        Page<Post> posts = postRepository.getPostListByKeyword(keywords,pageable);
        Page<PostMainDto> map = posts.map(post ->
                PostMainDto.builder()
                        .title(post.getTitle())
                        .id(post.getId())
                        .hashTagContentList(post.getHashTagList().stream().map(hashTag ->
                                hashTag.getTagContent()).collect(Collectors.toList()))
                        .imgUrl(post.getPostImgList().get(0).getImgUrl())
                        .score(post.getScore())
                        .likes(post.getLikeList().size())
                        .likedSiteUserNameList(postLikeRepository.getPostLikedSiteUserName(post))
                        .views(post.getViews())
                        .build()
        );
        return map;
    }

    public void createPost(PostFormDto postFormDto, List<MultipartFile> files, Principal principal) throws IOException {
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
        hashTagService.createHashTag(postFormDto.getTagContent(),principal,post);
    }

    public PostDetailDto getPostDetail(int postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("getPostDetail 과정 중 post가 없음!! "));
        return post.createPostDetailDto();
    }

    public SiteUser getSiteUser(int postId) {
        return postRepository.getSiteUser(postId);
    }


    public Post getPostById(int postId) {
        return postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException());
    }

    public void modifyPost(PostFormUpdateDto postFormUpdateDto, List<MultipartFile> files,int postId,Principal principal) throws IOException {
        Post post=postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException());
        post.setTitle(postFormUpdateDto.getTitle());
        post.setContent(postFormUpdateDto.getContent().replace("\r\n","<br/>"));
            for (int i = 0; i < files.size(); i++) {
                int  postImgId;
               try{
                   postImgId=Integer.parseInt(postFormUpdateDto.getPostImgIds().get(i));
               }catch (IndexOutOfBoundsException e){
                   //원래 이미지보다 많은경우
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
                 postImgService.deletePostImgModify(postImgId);
                 PostImg postImg = new PostImg();
                 postImg.setPost(post);

                 if (i ==0) {
                     postImg.setThumbnailYn(true);
                 } else {
                     postImg.setThumbnailYn(false);
                 }
                 //이미지변경이 아닌 바로 아래 이미지가 올라와진경우
                 if(files.get(i).isEmpty()){
                     PostImg postImgNext=postImgRepository.findById(Integer.parseInt(postFormUpdateDto.getPostImgIds().get(i+1))).get();
                     postImg.updatePostImg(postImgNext.getOriImgName(),postImgNext.getImgName(),postImgNext.getImgUrl());
                     postImgRepository.save(postImg);
                     continue;
                 }
                 postImgService.createPostImg(postImg, files.get(i));
             }
             }

            if(files.size()<postFormUpdateDto.getPostImgIds().size()){
               for(int j=files.size();j< postFormUpdateDto.getPostImgIds().size();j++){
                   //현재 postImg가 이전꺼에서 가져온것이면 파일삭제하지않고 postImg 객체만 삭제
                   postImgService.deletePostImgModifyDuple(Integer.parseInt(postFormUpdateDto.getPostImgIds().get(j)));
               }
            }
            hashTagService.modifyHashTag(postFormUpdateDto.getTagContent(),post,principal);
        }


    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }

    public void increaseView(int postId) {
        postRepository.increaseView(postId);
    }
}