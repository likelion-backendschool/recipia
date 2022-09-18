package com.ll.exam.RecipiaProject.user;

import com.ll.exam.RecipiaProject.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long>, BaseRepository {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


    Optional<SiteUser> findByUsername(String username);

    Optional<SiteUser> findByEmail(String email);
    @Query(value = "truncate table site_user",nativeQuery = true)
    @Modifying
    @Transactional
    void truncate();
}
