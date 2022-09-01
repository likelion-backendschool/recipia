package com.ll.exam.RecipiaProject.mypage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성(필드 final X force로 가능)
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬(null도 포함)
@Setter
@Getter
// getter,setter default -> public
public class MyPageDto {
}
