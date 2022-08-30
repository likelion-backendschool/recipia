package com.ll.exam.RecipiaProject.hashtag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Builder
    public HashTag(int tagId, String tagContent, int tagView, String tagCategory){
        this.tagId = tagId;
        this.tagContent= tagContent;
        this.tagView= tagView;
        this.tagCategory = tagCategory;
    }
}
