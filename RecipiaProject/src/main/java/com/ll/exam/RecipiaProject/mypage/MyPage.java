package com.ll.exam.RecipiaProject.mypage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class MyPage {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true) //중복 허용 X
    private String email;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String nickname;

    private String passward;
}
