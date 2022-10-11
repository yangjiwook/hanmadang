package com.kh.Ulsan_Hanmadang.domain.api.dao;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;

import java.util.List;

public interface ApiPubDAO {
  /**
   * 공연정보 저장
   * @param pEvents 공연정보
   * @return 등록건수
   */
  String savePEvent(List<PEvent> pEvents);

  /**
   * 공연장 정보 저장
   * @param pFacilities 공연장상세
   * @return 등록건수
   */
  int savePFacility(List<PFacility> pFacilities);

  /**
   * 게시물 아이디생성
   */
  Long generatePubPostId();
}
