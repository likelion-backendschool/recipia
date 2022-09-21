package com.ll.exam.RecipiaProject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private int userId;
    private int postId;
    private String content;
}
