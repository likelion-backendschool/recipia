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

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PostImg> postImgList;

    public PostDetailDto createPostDetailDto(){
        List<PostImgDto> postImgDtoList=new ArrayList<>();
        for(PostImg postImg:postImgList){
            if(postImg.getThumbnailYn()){
                postImgDtoList.add(0,postImg.createPostImgDto());
            }else{
                postImgDtoList.add( postImg.createPostImgDto());
            }
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
    //postFormDto 변환
    public PostFormDto createPostFormDto() {

        List<PostImgDto> pids=new ArrayList<>();
        List<String> pidIds=new ArrayList<>();

        //List<postImgDto>로 변환해주는과정
        for(PostImg pi : postImgList){
            PostImgDto pid= pi.createPostImgDto();
            //
            if(pi.getThumbnailYn()){
                pids.add(0,pid);
                pidIds.add(0,pid.getId()+"");
            }else{
                pids.add(pid);
                pidIds.add(pid.getId()+"");
            }
        }
        //postForDto 생성
        PostFormDto postFormDto=PostFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .postImgDtoList(pids)
                .postImgDtoIds(pidIds)
                .build();
        return postFormDto;
    }
}
