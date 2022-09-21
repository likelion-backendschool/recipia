package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    private String tagContent;

    private int tagView;

    private String tagCategory;

    @ManyToOne(optional = false)
    @JoinColumn(name = "site_user_id")
    private SiteUser siteUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

}
