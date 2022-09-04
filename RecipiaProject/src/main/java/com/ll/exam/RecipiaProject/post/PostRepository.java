package com.ll.exam.RecipiaProject.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    public Post findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE Post AUTO_INCREMENT=1",nativeQuery = true)
    public void truncate();

    @Query("select new com.ll.exam.RecipiaProject.post.PostDetailDto(p.id ,p.title ,p.content,p.score ,p.views,p.likes ,pi.imgUrl )  " +
            "from PostImg pi " +
            "join pi.post p " +
            "where p.id = :postId " +
            "and pi.thumbnailYn = true ")
    PostDetailDto getPostDetail(@Param("postId") int postId);
}
