package com.ll.exam.RecipiaProject.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


    Optional<SiteUser> findByUsername(String username);

}
