package com.kh.Ulsan_Hanmadang.web.form.post;

import com.kh.Ulsan_Hanmadang.domain.post.Promotion;
import lombok.Data;

@Data
public class DetailForm {
  private Long postId;           //  게시글번호
  private String pcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private String pcontent;      //  내용 BCONTENT	CLOB
  private int hit;              //  조회수

  private Promotion promotion;
}
