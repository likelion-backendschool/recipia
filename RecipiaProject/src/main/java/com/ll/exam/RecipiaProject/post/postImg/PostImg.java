package com.ll.exam.RecipiaProject.post.postImg;

import com.ll.exam.RecipiaProject.post.Post;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PostImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private Boolean thumbnailYn;

    @ManyToOne
    private Post post;

    public void updatePostImg(String oriImgName,String imgName,String imgUrl){
            this.oriImgName=oriImgName;
            this.imgName=imgName;
            this.imgUrl=imgUrl;
    }
    public PostImgDto createPostImgDto(){
        PostImgDto postImgDto=PostImgDto.builder()
                .id(id)
                .imgName(imgName)
                .oriImgName(oriImgName)
                .imgUrl(imgUrl)
                .thumbnailYn(thumbnailYn)
                .build();
        return postImgDto;
    }
}
