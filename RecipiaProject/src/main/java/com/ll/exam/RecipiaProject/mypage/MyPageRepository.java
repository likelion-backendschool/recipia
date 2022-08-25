package com.ll.exam.RecipiaProject.mypage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageRepository extends JpaRepository<MyPage, Integer> {

    MyPage findById(String id);

    MyPage findByNickName(String nickname);

    MyPage findByEmail(String email);

    MyPage findByUserName(String username);
}
