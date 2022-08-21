package com.ll.exam.RecipiaProject.board;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
}
