package com.kh.Ulsan_Hanmadang.domain.admin.dao;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AdminPostDAOImpl implements AdminPostDAO{

  private final JdbcTemplate jt;
  @Override
  public int add(PEvent pEvent) {
    return 0;
  }

  @Override
  public int update(String id, PEvent pEvent) {
    return 0;
  }

  @Override
  public PEvent findById(String pEventId) {
    return null;
  }

  @Override
  public int del(String pEventId) {
    return 0;
  }

  /**
   * 공연정보 목록
   * @return 전체 공연 정보 목록
   */
  @Override
  public List<PEvent> pEventList() {
    StringBuffer sql = new StringBuffer();

    sql.append("select event_id, mt20id, mt10id, prfnm,prfpdfrom,prfpdto,fcltynm,prfcast,prfruntime, ");
    sql.append("       prfage,pcseguidance,genrenm,prfstate,dtguidance, poster ");
    sql.append("  from p_event ");

    return jt.query(sql.toString(), new BeanPropertyRowMapper<>(PEvent.class));
  }


  /**
   * 공연시설 목록
   * @return 공연시설 정보 목록
   */
  @Override
  public List<PFacility> pFacilityList() {
    StringBuffer sql = new StringBuffer();

    sql.append("select mt10id,fcltynm,mt13cnt,fcltychartr,seatscale,telno,relateurl, ");
    sql.append("       adres,opende,la,lo ");
    sql.append("  from p_facility ");

    return jt.query(sql.toString(), new BeanPropertyRowMapper<>(PFacility.class));
  }
}
