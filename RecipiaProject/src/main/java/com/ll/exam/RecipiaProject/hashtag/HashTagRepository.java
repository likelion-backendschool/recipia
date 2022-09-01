package com.ll.exam.RecipiaProject.hashtag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {
    HashTag findByTagContent(String tagContent);
}
