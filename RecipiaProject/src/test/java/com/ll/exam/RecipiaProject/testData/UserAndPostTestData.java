package com.ll.exam.RecipiaProject.testData;

import com.ll.exam.RecipiaProject.post.PostController;
import com.ll.exam.RecipiaProject.post.PostFormDto;
import com.ll.exam.RecipiaProject.user.UserController;
import com.ll.exam.RecipiaProject.user.UserFormDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserAndPostTestData {
    @Autowired
    private UserController userController;

    @Autowired
    private PostController postController;

    @Autowired
    private MockMvc mockMvc;

    private static List<UserFormDto>users=new ArrayList<>();
    private static List<PostFormDto>posts=new ArrayList<>();
    @BeforeAll
    public  static void init(){
        UserFormDto user1 =UserFormDto.builder()
                .username("user1")
                .password1("user1")
                .password2("user1")
                .email("user1@naver.com")
                .build();

        UserFormDto user2=UserFormDto.builder()
                .username("user2")
                .password1("user2")
                .password2("user2")
                .email("user2@naver.com")
                .build();
        users.add(user1);
        users.add(user2);

        PostFormDto post1=PostFormDto.builder()
                .id(1)
                .title("제목1")
                .content("내용1")
                .build();

        PostFormDto post2=PostFormDto.builder()
                .id(2)
                .title("제목2")
                .content("내용2")
                .build();
        posts.add(post1);
        posts.add(post2);
    }
    @Test
    @Rollback(value = false)
    public void userInitUsingController() throws Exception {
        mockMvc.perform(post("/user/sign-up")
                        .with(csrf())
                        .param("username",users.get(0).getUsername())
                        .param("password1",users.get(0).getPassword1())
                        .param("password2",users.get(0).getPassword2())
                        .param("email", users.get(0).getEmail()))
                .andExpect(status().is3xxRedirection())
                .andDo(print());

        mockMvc.perform(post("/user/sign-up")
                        .with(csrf())
                        .param("username",users.get(1).getUsername())
                        .param("password1",users.get(1).getPassword1())
                        .param("password2",users.get(1).getPassword2())
                        .param("email", users.get(1).getEmail()))
                .andExpect(status().is3xxRedirection())
                .andDo(print());


    }
    @Test
    @Rollback(value = false)
    public void postInitUsingController() throws Exception {
        String fileName="test1";
        String contentType="jpg";
        String filePath="C:/post/img/"+fileName+"."+contentType;
        MockMultipartFile image1=new MockMultipartFile("imgFile",fileName+"."+contentType,"image/"+contentType,new FileInputStream(filePath));

        String fileName2="test2";
        String contentType2="jpg";
        String filePath2="C:/post/img/"+fileName2+"."+contentType2;
        MockMultipartFile image2=new MockMultipartFile("imgFile",fileName2+"."+contentType2,"image/"+contentType,new FileInputStream(filePath2));

        mockMvc.perform(
                multipart("/posts")
                        .file(image1)
                        .file(image2)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .param("title","title1").param("content","content1")
                        .with(csrf())
                        .with(user("user1").password("user1").roles("USER")))
                .andExpect(status().is3xxRedirection());

    }
}
