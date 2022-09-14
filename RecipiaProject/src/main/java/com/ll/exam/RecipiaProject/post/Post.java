package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;


    private int score;

    private int views ;

    private int likes;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifiedDate;

    @ManyToOne
    private SiteUser siteUser;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<PostImg> postImgList;

    public PostDetailDto createPostDetailDto(){
        List<PostImgDto> postImgDtoList=new ArrayList<>();
        for(PostImg postImg:postImgList){
            postImgDtoList.add( postImg.createPostImgDto());
        }
        PostDetailDto postDetailDto=PostDetailDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .score(score)
                .views(views)
                .likes(likes)
                .username(siteUser.getUsername())
                .postImgDtoList(postImgDtoList)
                .build();
        return postDetailDto;
    }
}
