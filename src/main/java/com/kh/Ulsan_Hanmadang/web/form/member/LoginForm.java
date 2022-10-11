package com.kh.Ulsan_Hanmadang.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
  @NotBlank
  @Email(regexp = ".+@.+\\..+")
  private String email;              //이메일
  @NotBlank
  @Size(min = 0, max=20)
  private String password;          //비밀번호
}
