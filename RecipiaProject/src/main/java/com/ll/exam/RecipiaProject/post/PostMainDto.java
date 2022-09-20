package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.hashtag.HashTag;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostMainDto {

    private int id;

    private String title;

    private int score;

    private int views ;

    private int likes;

    private String imgUrl;

    private List<String> hashTagContentList;

    public PostMainDto(int id, String title, int score, int views, int likes, String imgUrl) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.views = views;
        this.likes = likes;
        this.imgUrl = imgUrl;
    }
}
