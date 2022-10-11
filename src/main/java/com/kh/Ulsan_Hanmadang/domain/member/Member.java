package com.kh.Ulsan_Hanmadang.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
  private Long member_id;                // 식별 아이디
  private String email;           // 회원 실제 사용 아이디 (이메일)
  private String nickname;        // 닉네임
  private String name;            // 이름
  private String password;        // 패스워드
  private String phone;           // 전화번호
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date birthday;          // 생일 ex)2022-01-01
  private Boolean sms_service;    // sms 서비스
  private Boolean email_service;  // 이메일 서비스
  private String gubun;           // 회원 타입 ( 일반, 홍보, 관리)
  @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
  private LocalDateTime cdate;    //가입일
  @DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
  private LocalDateTime udate;    //수정일


}
