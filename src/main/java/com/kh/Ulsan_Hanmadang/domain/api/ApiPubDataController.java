package com.kh.Ulsan_Hanmadang.domain.api;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.api.svc.ApiPubSVC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pub")
@RequiredArgsConstructor
public class ApiPubDataController {

  private final ApiPubSVC apiExplorer;

  @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<PEvent> getJsonData(){
    List<PEvent> res = apiExplorer.apiCall("20220901", "20221031");
    return res;
  }

  @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public  List<PEvent> getData(){
    List<PEvent> res = apiExplorer.apiCall("20220901", "20221031");
    return res;
  }
}
