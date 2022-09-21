package com.ll.exam.RecipiaProject.comment.service;

import com.ll.exam.RecipiaProject.comment.dto.CommentDto;
import com.ll.exam.RecipiaProject.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void createComment(Principal principal, int postId, CommentDto dto) {


    }
}
