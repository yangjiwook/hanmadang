package com.kh.Ulsan_Hanmadang.web.form.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventInfoForm {
  private Long eventId;        //내부관리용 ID
  private String mt20id;        // MT20ID VARCHAR2(12 BYTE)	No		1
  private String prfnm;         // PRFNM	VARCHAR2(100 BYTE)	Yes		2
  private String prfpdfrom;     // PRFPDFROM	VARCHAR2(10 BYTE)	Yes		3
  private String prfpdto;       // PRFPDTO	VARCHAR2(10 BYTE)	Yes		4
  private String fcltynm;       // FCLTYNM	VARCHAR2(60 BYTE)	Yes		5
  private String prfcast;       // PRFCAST	VARCHAR2(100 BYTE)	Yes		11
  private String prfcrew;       // PRFCREW	VARCHAR2(30 BYTE)	Yes		12
  private String prfruntime;    // PRFRUNTIME	VARCHAR2(15 BYTE)	Yes		13
  private String prfage;        // PRFAGE	VARCHAR2(20 BYTE)	Yes		14
  private String entrpsnm;
  private String pcseguidance;  // PCSEGUIDANCE	NUMBER(6,0)	Yes		15
  private String poster;        // POSTER	VARCHAR2(100 BYTE)	Yes		6
  private String sty;           // STY	CLOB	Yes		16
  private String genrenm;       // GENRENM	VARCHAR2(10 BYTE)	Yes		7
  private String prfstate;      // PRFSTATE	VARCHAR2(12 BYTE)	Yes		8
  private String openrun;       // OPENRUN	VARCHAR2(1 BYTE)	Yes		9

  private List<String> styurls;
  private String mt10id;        // MT10ID	VARCHAR2(12 BYTE)	Yes		10
  private String dtguidance;    // DTGUIDANCE	VARCHAR2(100 BYTE)	Yes		17

  private Integer hit;          //조회수
  private Integer good;         //좋아요
}