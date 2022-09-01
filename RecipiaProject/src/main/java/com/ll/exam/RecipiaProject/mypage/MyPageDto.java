package com.ll.exam.RecipiaProject.mypage;

import com.ll.exam.RecipiaProject.user.SiteUser;
import lombok.*;

@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성(필드 final X force로 가능)
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬(null도 포함)
@Setter
@Getter
@Builder
// getter,setter default -> public
public class MyPageDto {

    private Integer id;

    private String username;

    private String email;

    private String nickname;

    private String gender;

    public static MyPageDto createMyPageDto(SiteUser siteUser){
        return MyPageDto.builder()
                .id(siteUser.getId())
                .username(siteUser.getUsername())
                .email(siteUser.getEmail())
                .nickname(siteUser.getNickname())
                .gender(siteUser.getGender())
                .build();
    }
}
