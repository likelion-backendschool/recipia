package com.ll.exam.RecipiaProject.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
//    static File file=new File("C:\\Users\\KIM\\Desktop\\p1.png");
    @BeforeEach
    public void beforeEach() throws IOException {
        clearData();
        createSampleData();
    }
    private void clearData(){
        postRepository.deleteAll();
        postRepository.truncate();
    }
    private void createSampleData() throws IOException {
        Post post=null;
        for(int i=1;i<=6;i++){
            post =Post.builder()
                    .title("title%s".formatted(i))
                    .content("content%s".formatted(i))
                    //.image(Files.readAllBytes(file.toPath()))
                    .score(1*i)
                    .views(2*i)
                    .likes(3*i)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .modifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                    .build();
            postRepository.save(post);
        }
    }
    @Test
    @Transactional
    @Rollback(value = false)
    public void save(){
      Post post = postRepository.findByTitle("title6");
        Assertions.assertEquals("content6",post.getContent());
    }
}