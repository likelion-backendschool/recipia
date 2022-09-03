package com.ll.exam.RecipiaProject.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post,Integer> {
    public Post findByTitle(String title);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE Post AUTO_INCREMENT=1",nativeQuery = true)
    public void truncate();

    //제목 + 내용 + 글쓴이 정보 찾기
    Page<Post> findByTitleOrContentOrSiteUser(String kw, String kw_, String kw__, Pageable pageable);
}
