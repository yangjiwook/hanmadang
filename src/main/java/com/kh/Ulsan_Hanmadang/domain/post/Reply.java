package com.kh.Ulsan_Hanmadang.domain.post;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class Reply {
  private Long replyId;
  private Long postId;
  private String pcategory;     //  분류 PCATEGORY	VARCHAR2(11 BYTE)
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  private String rcontent;      //  내용 BCONTENT	CLOB
  private LocalDateTime cdate;  //  생성일 CDATE	TIMESTAMP(6)
  private LocalDateTime udate;  //  수정일 UDATE	TIMESTAMP(6)

  private List<MultipartFile> files;  // 첨부파일
}
