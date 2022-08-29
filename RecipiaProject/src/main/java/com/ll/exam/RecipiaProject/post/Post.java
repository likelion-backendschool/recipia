package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;


    private int score;

    private int views;

    private int likes;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp modifiedDate;

    private Timestamp deleteDate;

    private boolean isBlind;

    @ManyToOne
    private SiteUser siteUser;

    @Builder
    public Post(String title, String content, int score, int views, int likes, Timestamp createdDate, Timestamp modifiedDate, Timestamp deleteDate, boolean isBlind,SiteUser siteUser){
        this.title=title;
        this.content=content;
        this.score=score;
        this.views=views;
        this.likes=likes;
        this.createdDate=createdDate;
        this.modifiedDate=modifiedDate;
        this.deleteDate=deleteDate;
        this.isBlind=isBlind;
        this.siteUser=siteUser;
    }
}
