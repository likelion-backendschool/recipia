package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.base.entity.BaseTime;
import com.ll.exam.RecipiaProject.comment.entity.Comment;
import com.ll.exam.RecipiaProject.hashtag.HashTag;
import com.ll.exam.RecipiaProject.post.postImg.PostImg;
import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.post.postLike.PostLike;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    @Lob
    private String content;


    private int score;

    private int views ;

    private int likes;
    @ManyToOne
    private SiteUser siteUser;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE,orphanRemoval = true)
    @Builder.Default
    private List<PostImg> postImgList=new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<HashTag> hashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @OrderBy("id desc")
    @Builder.Default
    private List<Comment> commentList=new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<PostLike> likeList=new ArrayList<>();

    public PostDetailDto createPostDetailDto(){
        List<PostImgDto> postImgDtoList=new ArrayList<>();
        List<String> tageContentList=new ArrayList<>();
        List<Comment> replyList= new ArrayList<>();
        for(PostImg postImg:postImgList){
            if(postImg.getThumbnailYn()){
                postImgDtoList.add(0,postImg.createPostImgDto());
            }else{
                postImgDtoList.add( postImg.createPostImgDto());
            }
        }
        for(HashTag hashTag:hashTagList){
            tageContentList.add(hashTag.getTagContent());
        }
        for(Comment comment:commentList){
            replyList.add(comment);
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
                .hashTagContentList(tageContentList)
                .commentList(replyList)
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
        StringBuilder tagContent=new StringBuilder();
        for(HashTag hashTag:hashTagList){

            tagContent.append("#"+hashTag.getTagContent());
        }
        //postFormDto 생성
        PostFormDto postFormDto=PostFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .postImgDtoList(pids)
                .postImgDtoIds(pidIds)
                .tagContent(tagContent.toString())
                .build();
        return postFormDto;
    }
}
