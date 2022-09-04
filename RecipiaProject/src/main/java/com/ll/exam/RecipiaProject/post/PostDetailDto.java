package com.ll.exam.RecipiaProject.post;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {

    private int id;

    private String title;

    private String content;

    private int score;

    private int views ;

    private int likes;

    private String imgUrl;
}