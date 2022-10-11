package com.kh.Ulsan_Hanmadang.domain.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReplyReq {
  private String postId;
  private String pcategory;     //  분류 PCATEGORY	VARCHAR2(11 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private String rcontent;      //  내용 BCONTENT	CLOB

}
