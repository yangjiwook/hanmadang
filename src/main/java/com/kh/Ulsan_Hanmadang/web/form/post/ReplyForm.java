package com.kh.Ulsan_Hanmadang.web.form.post;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ReplyForm {

  @NotBlank
  @Size(min=5,max = 11)
  private String bcategory;     //  분류 BCATEGORY	VARCHAR2(11 BYTE)
  @NotBlank
  @Size(min=5,max=50)
  private String title;         //  제목 TITLE	VARCHAR2(150 BYTE)
  @NotBlank
  @Email
  private String email;         //  EMAIL	VARCHAR2(50 BYTE)
  @NotBlank
  @Size(min=3,max=15)
  private String nickname;      //  별칭 NICKNAME	VARCHAR2(30 BYTE)
  @NotBlank
  @Size(min=5)
  private String bcontent;      //  내용 BCONTENT	CLOB

  private List<MultipartFile> files;  // 첨부파일
}
