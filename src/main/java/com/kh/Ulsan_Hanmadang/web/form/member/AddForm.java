package com.kh.Ulsan_Hanmadang.web.form.member;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
public class AddForm {
  @NotBlank
  @Email(regexp = ".+@.+\\..+")
  private String email;           // 이메일
  @NotBlank
  @Size(min = 4, max=20)
  private String password;        // 비밀번호
  @NotBlank
  private String name;            // 이름
  @NotBlank
  @Size(min = 2, max=10)
  private String nickname;        // 닉네임
  @NotBlank
  @Size(min = 8, max=12)
  private String phone;           // 전화번호

  private Date birthday; // 생일

  private Boolean sms_service;    // sms 서비스
  private Boolean email_service;  // 이메일 서비스
  private Gubun gubun;
}