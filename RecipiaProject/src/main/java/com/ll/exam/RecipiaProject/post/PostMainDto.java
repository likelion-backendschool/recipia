package com.ll.exam.RecipiaProject.post;

import lombok.*;

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

    //좋아요 버튼을 누른 사용자의 좋아요 배경색 체크를 위한 변수
    private List<String> likedSiteUserNameList;
    public PostMainDto(int id, String title, int score, int views, int likes, String imgUrl) {
        this.id = id;
        this.title = title;
        this.score = score;
        this.views = views;
        this.likes = likes;
        this.imgUrl = imgUrl;
    }
}
