package com.ll.exam.RecipiaProject.comment.service;

import com.ll.exam.RecipiaProject.comment.dto.CommentDto;
import com.ll.exam.RecipiaProject.comment.entity.Comment;
import com.ll.exam.RecipiaProject.comment.repository.CommentRepository;
import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.post.PostRepository;
import com.ll.exam.RecipiaProject.user.SiteUser;
import com.ll.exam.RecipiaProject.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createComment(int postId,CommentDto dto,Principal principal) {

        SiteUser user = userRepository.findByUsername(principal.getName()).orElseThrow(()-> {
            return new IllegalArgumentException("댓글 쓰기 실패, 유저를 찾을 수 없습니다.");
        });

        Post post = postRepository.findById(postId).orElseThrow(()-> {
            return new IllegalArgumentException("댓글 쓰기 실패, 게시글 id를 찾을 수 없습니다.");
        });

        Comment comment = Comment.builder()
                .siteUser(user)
                .post(post)
                .content(dto.getContent())
                .build();
        commentRepository.save(comment);
    }

    public void deleteComment(long commentId, Principal principal) {
        if (principal.getName().equals(CommentAuthor(commentId).getUsername())){
            commentRepository.deleteById(commentId);
        }
    }

    public SiteUser CommentAuthor(long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> { return new IllegalArgumentException("댓글 사용자를 찾을 수 없습니다.");}
        );
        SiteUser user = comment.getSiteUser();
        return user;
    }
}
