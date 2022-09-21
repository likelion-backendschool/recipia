package com.ll.exam.RecipiaProject.allergy;

import com.ll.exam.RecipiaProject.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AllergyRepository extends JpaRepository<Allergy, Integer> {

    Allergy findByAllergyContent(String allergyContent);

    @Query(value = "truncate table allergy",nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();

}
