package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {

    private int id;

    private String title;

    private String content;

    private int score;

    private int views ;

    private int likes;

    private String username;

    private List<String> hashTagContentList;

    private List<PostImgDto>postImgDtoList=new ArrayList<>();
}
