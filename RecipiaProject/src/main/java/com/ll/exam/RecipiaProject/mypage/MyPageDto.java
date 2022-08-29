package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;

public class MyPageDto {

    private Integer id;

    private String email;

    private String username;

    private String nickname;

    private String passward;
    public SiteUser siteUser() {
        return SiteUser.builder()
                .id(id)
                .email(email)
                .username(username)
                .nickname(nickname)
                .password(passward)
                .build();
    }

}
