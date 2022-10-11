package com.kh.Ulsan_Hanmadang.web.form.post;

import lombok.Data;

@Data
public class FacInfoForm {
  private String mt10id; //	    VARCHAR2(10), --	pk, fk, 공연시설ID
  private String fcltynm; //	    VARCHAR2(100), --	fk, 공연시설명
  private String mt13cnt; //	    VARCHAR2(5), --	공연장 수
  private String fcltychartr; //	VARCHAR2(30), --	시설특성
  private String seatscale; //	  VARCHAR2(10), --	5	객석 수
  private String telno; //	      VARCHAR2(15), --	전화번호
  private String relateurl; //	  VARCHAR2(100), --	홈페이지
  private String adres; //	      VARCHAR2(120), --	주소
  private String opende;  //	    VARCHAR2(6), --	개관연도
  private String la;  //	        VARCHAR2(20), --	위도
  private String lo;  //	        VARCHAR2(25) --	경도
}
