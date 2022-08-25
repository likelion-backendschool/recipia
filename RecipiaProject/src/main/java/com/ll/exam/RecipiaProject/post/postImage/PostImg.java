package com.ll.exam.RecipiaProject.post.postImage;

import com.ll.exam.RecipiaProject.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PostImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private boolean thumbnailYn;

    @ManyToOne
    private Post post;

    @Builder
    public PostImg(String imgName,String oriImgName,String imgUrl,boolean thumbnailYn){
        this.imgName=imgName;
        this.oriImgName=oriImgName;
        this.imgUrl=imgUrl;
        this.thumbnailYn=thumbnailYn;
    }
}
