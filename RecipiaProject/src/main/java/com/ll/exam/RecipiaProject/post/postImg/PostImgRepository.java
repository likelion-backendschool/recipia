package com.ll.exam.RecipiaProject.post.postImg;

import com.ll.exam.RecipiaProject.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PostImgRepository extends JpaRepository<PostImg, Integer>, BaseRepository {


    @Query(value = "truncate table post_img",nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();
}
