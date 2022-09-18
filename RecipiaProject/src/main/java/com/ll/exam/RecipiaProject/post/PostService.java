package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.hashtag.HashTag;
import com.ll.exam.RecipiaProject.hashtag.HashTagRepository;
import com.ll.exam.RecipiaProject.post.postImg.PostImg;
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
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;

    private final PostImgService postImgService;

    public Page<PostMainDto> getPostList(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);
        Page<PostMainDto> map = posts.map(post ->
                PostMainDto.builder()
                        .title(post.getTitle())
                        .id(post.getId())
                        .hashTagContentList(post.getHashTagList().stream().map(hashTag ->
                                hashTag.getTagContent()).collect(Collectors.toList()))
                        .imgUrl(post.getPostImgList().get(0).getImgUrl())
                        .score(post.getScore())
                        .likes(post.getLikes())
                        .views(post.getViews())
                        .build()
        );


        return map;
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


        String[] tags = postFormDto.getTagContent().split("#");
        for(String tag: tags){
            tag = tag.trim();
            if(tag.length() == 0 ) continue;
            HashTag h = HashTag.builder()
                    .tagContent(tag)
                    .siteUser(user)
                    .post(post)
                    .build();
            post.getHashTagList().add(h);
        }
    }

    public PostDetailDto getPostDetail(int postId) {
        Post post=postRepository.getPostDetail(postId);
        return post.createPostDetailDto();
    }
}
