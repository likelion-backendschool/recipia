package com.ll.exam.RecipiaProject.post.postImage;

import com.ll.exam.RecipiaProject.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private boolean ThumbnailYn;

    @ManyToOne
    private Post post;

}
