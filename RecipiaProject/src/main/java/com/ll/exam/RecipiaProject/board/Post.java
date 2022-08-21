package com.ll.exam.RecipiaProject.board;

import com.ll.exam.RecipiaProject.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    @Lob
    private byte[] image;

    private int score;

    private int views ;

    private int likes;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp modifiedDate;

    private Timestamp deleteDate;

    private boolean isBlind;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
