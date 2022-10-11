package com.kh.Ulsan_Hanmadang.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PFacility {
  private String mt10id;          //    MT10ID	VARCHAR2(10 BYTE)
  private String fcltynm;         //    FCLTYNM	VARCHAR2(50 BYTE)
  private String mt13cnt;         //    MT13CNT	NUMBER(2,0)
  private String fcltychartr;     //    FCLTYCHARTR	VARCHAR2(30 BYTE)
  private String seatscale;       //    SEATSCALE	NUMBER(5,0)
  private String telno;           //    TELNO	VARCHAR2(12 BYTE)
  private String relateurl;       //    RELATEURL	VARCHAR2(100 BYTE)
  private String adres;           //    ADRES	VARCHAR2(120 BYTE)
  private String opende;          //    OPENDE	NUMBER(4,0)
  private String la;              //    LA	VARCHAR2(20 BYTE)
  private String lo;              //    LO	VARCHAR2(20 BYTE)
}