package com.ll.exam.RecipiaProject.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PasswordDto {
    @NotEmpty(message = "새비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 재입력은 필수입니다.")
    private String password2;
}
