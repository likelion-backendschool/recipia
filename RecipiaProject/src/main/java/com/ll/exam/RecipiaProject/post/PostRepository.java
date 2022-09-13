package com.ll.exam.RecipiaProject.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    public Post findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE Post AUTO_INCREMENT=1", nativeQuery = true)
    public void truncate();

    @Query("select p from PostImg pi inner join pi.post p where p.id=:postId")
    Post getPostDetail(@Param("postId") int postId);
    @Query("select new com.ll.exam.RecipiaProject.post.PostMainDto(p.id ,p.title ,p.score,p.views ,p.likes,pi.imgUrl) " +
            "from PostImg pi " +
            "join pi.post p " +
            "where pi.thumbnailYn = true ")
    Page<PostMainDto> getPostList(Pageable pageable);
}
