package com.ll.exam.RecipiaProject.hashtag;

import com.ll.exam.RecipiaProject.user.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    HashTag findByTagContent(String tagContent);
    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE hash_tag AUTO_INCREMENT=1", nativeQuery = true)
    public void truncate();
}