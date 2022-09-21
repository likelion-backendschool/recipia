package com.ll.exam.RecipiaProject.comment.repository;

import com.ll.exam.RecipiaProject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
