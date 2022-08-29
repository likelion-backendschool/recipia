package com.ll.exam.RecipiaProject.mypage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPageDto, Integer> {

    MyPageDto findById(String id);

    MyPageDto findByNickName(String nickname);

    MyPageDto findByEmail(String email);

    MyPageDto findByUserName(String username);
}
