package com.ll.exam.RecipiaProject.testData;

import com.ll.exam.RecipiaProject.post.PostController;
import com.ll.exam.RecipiaProject.post.PostFormDto;
import com.ll.exam.RecipiaProject.post.PostRepository;
import com.ll.exam.RecipiaProject.post.postImg.PostImgRepository;
import com.ll.exam.RecipiaProject.user.UserController;
import com.ll.exam.RecipiaProject.user.UserFormDto;
import com.ll.exam.RecipiaProject.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserAndPostTestData {
    @Autowired
    private UserController userController;

    @Autowired
    private PostController postController;

    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private  PostImgRepository postImgRepository;
    @Autowired
    private  UserRepository userRepository;

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
    //2명의 회원생성 user1,user2
    @Test
    @Transactional
    @Rollback(value = false)
    @Order(1)
    public void userInitUsingController() throws Exception {
        userRepository.disableForeignKeyCheck();
        userRepository.truncate();
        userRepository.enableForeignKeyCheck();

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

    // user1 에 post_img 2개를 가진 post 생성
    //user2 에 post_img 2개를 가진 post 생성
    //이미지 경로는 내부경로에 있는 src/main/resources/static/sampleImg/test1.png,src/main/resources/static/sampleImg/test2.png
    @Test
    @Transactional
    @Rollback(value = false)
    @Order(2)
    public void postInitUsingController() throws Exception {
        postRepository.disableForeignKeyCheck();
        postRepository.truncate();
        postImgRepository.truncate();
        postRepository.enableForeignKeyCheck();

        String fileName="test1";
        String contentType="jpg";
        String filePath="src/main/resources/static/sampleImg/"+fileName+"."+contentType;
        MockMultipartFile image1=new MockMultipartFile("imgFile",fileName+"."+contentType,"image/"+contentType,new FileInputStream(filePath));

        String fileName2="test2";
        String contentType2="jpg";
        String filePath2="src/main/resources/static/sampleImg/"+fileName2+"."+contentType2;
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


        String fileName3="test3";
        String contentType3="jpg";
        String filePath3="src/main/resources/static/sampleImg/"+fileName3+"."+contentType3;
        MockMultipartFile image3=new MockMultipartFile("imgFile",fileName3+"."+contentType3,"image/"+contentType3,new FileInputStream(filePath3));

        String fileName4="test4";
        String contentType4="jpg";
        String filePath4="src/main/resources/static/sampleImg/"+fileName4+"."+contentType4;
        MockMultipartFile image4=new MockMultipartFile("imgFile",fileName4+"."+contentType4,"image/"+contentType4,new FileInputStream(filePath4));

        mockMvc.perform(
                        multipart("/posts")
                                .file(image3)
                                .file(image4)
                                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                                .param("title","title2").param("content","content2")
                                .with(csrf())
                                .with(user("user2").password("user2").roles("USER")))
                .andExpect(status().is3xxRedirection());


    }
}
