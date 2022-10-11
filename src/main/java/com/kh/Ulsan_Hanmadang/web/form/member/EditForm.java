package com.kh.Ulsan_Hanmadang.web.form.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditForm {
  //private Long id;                //아이디
  private String email;           //이메일
  private String name;            //이름
  @NotBlank
  @Size(min = 2, max=10)
  private String nickname;        // 닉네임
  @NotBlank
  @Size(min = 1, max=12)
  private String phone;           // 전화번호
  private Date birthday; // 생일 ex)2022-01-01
  private Boolean sms_service;    // sms 서비스
  private Boolean email_service;  // 이메일 서비스
}