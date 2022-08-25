package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImage.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostFormDto {
private int id;

private String title;

private String content;

private List<PostImgDto> postImgDtoList=new ArrayList<>();

public Post createPost(SiteUser siteUser){
    return Post.builder()
            .title(title)
            .content(content)
            .score(0)
            .views(0)
            .likes(0)
            .siteUser(siteUser)
            .build();
}
}
