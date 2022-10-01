package com.ll.exam.RecipiaProject.comment.entity;

import com.ll.exam.RecipiaProject.base.entity.BaseTime;
import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment  extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;


    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private SiteUser siteUser;
}
