package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.post.Post;
import com.ll.exam.RecipiaProject.post.postImg.PostImgDto;
import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HashTagFormDto {
    private int tagId;
    private String tagContent;
    private int tagView;
    private String tagCategory;

    public HashTag createHashTag(SiteUser siteUser){
        return HashTag.builder()
                .tagId(tagId)
                .tagContent(tagContent)
                .tagView(tagView)
                .tagCategory(tagCategory)
                .siteUser(siteUser)
                .build();
    }

    public HashTag deleteHashTag(SiteUser siteUser){
        return HashTag.builder()
                .tagId(tagId)
                .tagContent(tagContent)
                .tagView(tagView)
                .tagCategory(tagCategory)
                .siteUser(siteUser)
                .build();
    }

}
