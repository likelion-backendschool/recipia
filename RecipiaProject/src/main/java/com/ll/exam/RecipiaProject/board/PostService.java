package com.ll.exam.RecipiaProject.board;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    public  List<Post> getPostList(){
        return postRepository.findAll();
    }
}
