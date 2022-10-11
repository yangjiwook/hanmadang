package com.kh.Ulsan_Hanmadang.domain.api.dao;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ApiPubDAOImpl implements ApiPubDAO{

  private final JdbcTemplate jt;

  @Override
  public String savePEvent(List<PEvent> pEvents) {

    String result = null;

    StringBuffer sql = new StringBuffer();
    Long eventId = generatePubPostId();

    for (PEvent pEvent : pEvents) {

      sql.append("insert into p_event (event_id, mt20id,prfnm,prfpdfrom,prfpdto,fcltynm,");
      sql.append("genrenm,prfstate,mt10id,prfcast,prfruntime,");
      sql.append("prfage,pcseguidance,poster,dtguidance) ");
      sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

      jt.update(sql.toString(), eventId, pEvent.getMt20id(), pEvent.getPrfnm(), pEvent.getPrfpdfrom(), pEvent.getPrfpdto(),
              pEvent.getFcltynm(), pEvent.getGenrenm(), pEvent.getPrfstate(), pEvent.getMt10id(), pEvent.getPrfcast(),
              pEvent.getPrfruntime(), pEvent.getPrfage(), pEvent.getPcseguidance(), pEvent.getPoster(), pEvent.getDtguidance());
      result = pEvent.getMt10id();
    }

    return result;
  }


  @Override
  public int savePFacility(List<PFacility> pFacilities) {
    int result = 0;

    StringBuffer sql = new StringBuffer();

    for (PFacility pFacility : pFacilities) {

      String mt10id = pFacility.getMt10id();
      sql.append("select count(mt10id)" );
      sql.append("  from p_facility ");
      sql.append(" where mt10id = ? ");

      Integer rowCount = jt.queryForObject(sql.toString(), Integer.class, mt10id);

      if(rowCount == 0) {

        sql = new StringBuffer();
        sql.append("insert into p_facility ");
        sql.append("values(?,?,?,?,?,?,?,?,?,?,?) ");

        result += jt.update(sql.toString(), pFacility.getMt10id(), pFacility.getFcltynm(), pFacility.getMt13cnt(),
                pFacility.getFcltychartr(), pFacility.getSeatscale(), pFacility.getTelno(), pFacility.getRelateurl(),
                pFacility.getAdres(), pFacility.getOpende(), pFacility.getLa(), pFacility.getLo());
      }
    }
    return result;
  }

  /**
   * 게시물 아이디생성
   */
  @Override
  public Long generatePubPostId() {
    String sql = "select p_event_post_id_seq.nextval from dual ";
    Long pubPostId = jt.queryForObject(sql, Long.class);
    return pubPostId;
  }
}

