package com.ll.exam.RecipiaProject.hashtag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    @Query(value = "SELECT * FROM HashTag WHERE tagContent LIKE :tagContent or tagContent LIKE concat('%,',:tagContent,',%') or tagContent like CONCAT('%,',:tagContent) or tagContent LIKE CONCAT(:tagContent,',%') order by tagId desc", nativeQuery = true)
    Page<HashTag> searchResult(String tagContent, Pageable pageable);
}
