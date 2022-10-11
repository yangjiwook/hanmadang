package com.kh.Ulsan_Hanmadang.web.form.member;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordForm {
  @NotBlank
  @Size(min = 0, max=20)
  private String password;          //비밀번호
}
