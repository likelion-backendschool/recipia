package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class PostFormDto {
    private int id;

    private String title;

    private String content;

    //상세페이지 구현시 이미지 전송 에 사용될 변수
    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    public Post createPost(SiteUser siteUser) {
        return Post.builder()
                .title(title)
                .content(content)
                .siteUser(siteUser)
                .build();
    }
}
