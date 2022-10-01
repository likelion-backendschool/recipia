package com.ll.exam.RecipiaProject.post.postLike;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLikeJsonDto {
    //로그인한 username
    String siteUserName;
    //누른 게시물 id
    String postId;
}
