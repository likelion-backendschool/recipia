package com.ll.exam.RecipiaProject.post;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFormUpdateDto {
    private String title;
    private String content;
    private List<String> imgNames;
    private List<String> postImgIds;
}
