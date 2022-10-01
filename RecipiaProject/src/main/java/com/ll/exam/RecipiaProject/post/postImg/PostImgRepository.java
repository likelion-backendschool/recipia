package com.ll.exam.RecipiaProject.post.postImg;

import com.ll.exam.RecipiaProject.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostImgRepository extends JpaRepository<PostImg, Integer>, BaseRepository {


    @Query(value = "truncate table post_img",nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();
    @Query(value = "select count(*) from PostImg pi where pi.imgName=:imgName")
    int getCountOfImgName(@Param("imgName")String imgName);

}
