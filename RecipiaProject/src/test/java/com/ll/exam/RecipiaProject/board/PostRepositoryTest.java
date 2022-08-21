package com.ll.exam.RecipiaProject.board;

import com.ll.exam.RecipiaProject.user.Users;
import com.ll.exam.RecipiaProject.user.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostRepository postRepository;
    static File file=new File("C:\\Users\\KIM\\Desktop\\p1.png");
    @Test
    public void save() throws IOException {
        Users users = Users.builder()
                .loginId("loginId")
                .loginPw("loginPw")
                .email("email")
                .nickname("nickname")
                .gender(true)
                .build();
        usersRepository.save(users);
        Users users1 = usersRepository.findById(users.getId()).orElseThrow(()->new EntityNotFoundException("문제발생"));
        Post post=null;
      for(int i=1;i<=8;i++){
         post =Post.builder()
                  .title("title%s".formatted(i))
                  .content("content%s".formatted(i))
                  .image(Files.readAllBytes(file.toPath()))
                  .score(1*i)
                  .views(2*i)
                  .likes(3*i)
                  .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                  .deleteDate(Timestamp.valueOf(LocalDateTime.now()))
                  .modifiedDate(Timestamp.valueOf(LocalDateTime.now()))
                  .isBlind(true)
                  .users(users1)
                  .build();
          postRepository.save(post);
      }
        Assertions.assertEquals("title8",post.getTitle());
    }
}