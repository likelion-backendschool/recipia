package com.ll.exam.RecipiaProject.post.postImg;


public class PostImgDto {

    private int id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private boolean thumbnailYn;

    public PostImg createPostImg(){
        return PostImg.builder()
                .imgName(imgName)
                .oriImgName(oriImgName)
                .imgUrl(imgUrl)
                .thumbnailYn(thumbnailYn)
                .build();
    }
}
