package com.kh.Ulsan_Hanmadang.domain.post;

import lombok.Data;

import java.sql.Date;

@Data
public class Promotion {
  private Long promotionId;
  private Long postId;      //홍보게시글번호
  private Date adStartDay;  //홍보이벤트시작일
  private Date adEndDay;    //홍보이벤트마감일
  private String entFee;    //참가비
}
