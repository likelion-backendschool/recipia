package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;
    private String tagContent;
    private int tagView;
    private String tagCategory;

    @ManyToOne
    private SiteUser siteUser;

    @Builder
    public HashTag(int tagId, String tagContent, int tagView, String tagCategory, SiteUser siteUser){
        this.tagId = tagId;
        this.tagContent= tagContent;
        this.tagView= tagView;
        this.tagCategory = tagCategory;
        this.siteUser = siteUser;
    }
}
