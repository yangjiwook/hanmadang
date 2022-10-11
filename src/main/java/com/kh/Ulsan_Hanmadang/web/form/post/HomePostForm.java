package com.kh.Ulsan_Hanmadang.web.form.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HomePostForm {
  private Long postId;          //  게시글 번호 BBS_ID	NUMBER(10,0)
  private String pcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private LocalDateTime udate;  //  수정일 UDATE	TIMESTAMP(6)
}
