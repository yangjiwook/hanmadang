package com.kh.Ulsan_Hanmadang.domain;

import lombok.Data;

@Data
public class PromotionHome {
  private Long uploadfileId;      //  UPLOADFILE_ID	NUMBER(10,0)
  private Long post_id;
  private Long rid;               //  RID	NUMBER(10,0)
  private String upload_filename; //  UPLOAD_FILENAME	VARCHAR2(50 BYTE)
}
