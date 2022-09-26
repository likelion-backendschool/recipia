package com.ll.exam.RecipiaProject.user;

import com.ll.exam.RecipiaProject.comment.entity.Comment;
import com.ll.exam.RecipiaProject.post.postLike.PostLike;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String gender;

    @OneToMany(mappedBy = "siteUser")
    @Builder.Default
    private List<Comment> commentList=new ArrayList<>();

}
