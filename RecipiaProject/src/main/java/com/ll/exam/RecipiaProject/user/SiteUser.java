package com.ll.exam.RecipiaProject.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String gender;


    // 객체 생성 패턴 Not Null방지를 해주자.
    @Builder
    public SiteUser(Integer id, String username, String password, String email, String nickname, String gender){
        Assert.notNull(id, "id must not be null");
        Assert.notNull(username, "username must not be null");
        Assert.notNull(password, "password must not be null");
        Assert.notNull(email, "email must not be null");
        Assert.notNull(nickname, "nickname must not be null");
        Assert.notNull(gender, "gender must not be null");


        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
    }
}
