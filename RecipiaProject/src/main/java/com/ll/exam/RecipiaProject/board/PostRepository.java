package com.ll.exam.RecipiaProject.board;

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
}
