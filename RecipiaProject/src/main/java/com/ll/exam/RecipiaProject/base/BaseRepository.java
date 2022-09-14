package com.ll.exam.RecipiaProject.base;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface BaseRepository {
    @Transactional
    @Modifying
    @Query(
            value = "SET FOREIGN_KEY_CHECKS = 0",
            nativeQuery = true
    )
    void disableForeignKeyCheck();

    @Transactional
    @Modifying
    @Query(
            value = "SET FOREIGN_KEY_CHECKS = 1",
            nativeQuery = true
    )
    void enableForeignKeyCheck();

}
