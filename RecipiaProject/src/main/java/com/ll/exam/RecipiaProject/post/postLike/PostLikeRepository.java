package com.ll.exam.RecipiaProject.post.postLike;

import com.ll.exam.RecipiaProject.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike,Integer> {
    @Query("select pl from PostLike pl " +
            "join pl.siteUser su " +
            "join pl.post p " +
            "where su.id = :siteUserId " +
            "And p.id = :postId ")
    Optional<PostLike> exist(@Param("siteUserId") int siteUserId, @Param("postId")int postId);

    @Query("select count(pl) from PostLike pl " +
            "join pl.post p " +
            "where p.id = :postId")
    int getPostLikeNum(@Param("postId") int postId);


    @Query("select su.username from PostLike pl " +
            "join pl.siteUser su " +
            "where pl.post = :post")
    List<String> getPostLikedSiteUserName(@Param("post") Post post);
}
