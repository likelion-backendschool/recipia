package com.ll.exam.RecipiaProject.post.postLike;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.post.PostRepository;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    //좋아요 버튼클릭시 이미 눌럿으면 좋아요 삭제,좋아요 버튼 처음누르면 좋아요 생성
    public void pushLikeButton(int siteUserId, int postId) {
        postLikeRepository.exist(siteUserId,postId).ifPresentOrElse(
                postLike -> postLikeRepository.deleteById(postLike.getId()),
                ()->{
                    Post post=postRepository.findById(postId).orElseThrow(()->new EntityNotFoundException("pushLikeButton 에서 게시물을 찾지못합니다!"));
                    SiteUser siteUser=userRepository.findById(siteUserId).orElseThrow(()->new EntityNotFoundException("pushLikeButton 에서 유저를 찾지못합니다!"));
                    PostLike postLike =PostLike.builder().post(post).siteUser(siteUser).build();
                    postLikeRepository.save(postLike);
                });
    }

    public int getPostLikeNum(int postId){
        return postLikeRepository.getPostLikeNum(postId);
    }
    public List<String> getPostLikedSiteUserName(Post post){
        return postLikeRepository.getPostLikedSiteUserName(post);
    }
}
