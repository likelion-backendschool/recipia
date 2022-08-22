package com.ll.exam.RecipiaProject.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
