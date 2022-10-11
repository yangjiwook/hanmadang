package com.kh.Ulsan_Hanmadang;

import com.kh.Ulsan_Hanmadang.domain.common.paging.FindCriteria;
import com.kh.Ulsan_Hanmadang.domain.common.paging.PageCriteria;
import com.kh.Ulsan_Hanmadang.domain.common.paging.RecordCriteria;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagingConfig {

  private static final  int REC_COUNT_12_PER_PAGE = 12;
  private static final  int PAGE_COUNT_12_PER_PAGE = 12;

  private static final  int REC_COUNT_10_PER_PAGE = 10;
  private static final  int PAGE_COUNT_10_PER_PAGE = 10;

  private static final  int REC_COUNT_5_PER_PAGE = 6;
  private static final  int PAGE_COUNT_5_PER_PAGE = 6;

  @Bean
  public RecordCriteria rc12(){
    return new RecordCriteria(REC_COUNT_12_PER_PAGE);
  }
  @Bean
  public PageCriteria pc12(){
    return new PageCriteria(rc12(), PAGE_COUNT_12_PER_PAGE);
  }
  @Bean
  public RecordCriteria rc10(){
    return new RecordCriteria(REC_COUNT_10_PER_PAGE);
  }

  @Bean
  public PageCriteria pc10(){
    return new PageCriteria(rc10(), PAGE_COUNT_10_PER_PAGE);
  }

  @Bean
  public RecordCriteria rc6(){
    return new RecordCriteria(REC_COUNT_5_PER_PAGE);
  }

  @Bean
  public PageCriteria pc6(){
    return new PageCriteria(rc10(), PAGE_COUNT_5_PER_PAGE);
  }

  @Bean
  public FindCriteria fc12() {
    return new FindCriteria(rc12(),PAGE_COUNT_10_PER_PAGE);
  }
  @Bean
  public FindCriteria fc10() {
    return new FindCriteria(rc10(),PAGE_COUNT_10_PER_PAGE);
  }

  @Bean
  public FindCriteria fc6() {
    return  new FindCriteria(rc6(),PAGE_COUNT_5_PER_PAGE);
  }
}
