package com.ll.exam.RecipiaProject.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String loginId;

    private String loginPw;

    private String email;

    private String nickname;

    private boolean gender;

    @Builder
    public Users(String loginId, String loginPw, String email, String nickname, boolean gender){
        this.loginId=loginId;
        this.loginPw=loginPw;
        this.email=email;
        this.nickname=nickname;
        this.gender=gender;

    }
}
