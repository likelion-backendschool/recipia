package com.ll.exam.RecipiaProject.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserFormDto {
    @Size(min = 3)
    @NotEmpty(message = "사용자 이름은 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 재입력은 필수입니다.")
    private String password2;

    @Email
    @NotEmpty(message = "이메일은 필수항목입니다.")
    private String email;
}
