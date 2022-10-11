package com.kh.Ulsan_Hanmadang.web;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;
import com.kh.Ulsan_Hanmadang.domain.admin.svc.AdminPostSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

  private final AdminPostSVC adminPostSVC;


  //공연목록
  @GetMapping("")
  public String map(Model model) {
    List<PEvent> eventList =adminPostSVC.pEventList();
    List<PFacility> facilityList = adminPostSVC.pFacilityList();

    List<PEvent> elist = new ArrayList<>();
    List<PFacility> flist = new ArrayList<>();


    // PEvent와 PFacility에서 공연장 번호가 같은 리스트만 추출하여 저장
    // => 현재 공연중인 공연장의 정보만 나옴
    for(PEvent e : eventList){
      for(PFacility f : facilityList){

        // 공연장 시설번호가 같은지 확인
        if(f.getMt10id().equals(e.getMt10id())) {

          // 주소 뒤의 ( 이후 문자 제거
          // => 정확한 주소 검색을 위한 코드
          String addres = f.getAdres();

          if(addres.contains("(")){
            int idx = addres.indexOf("(");
            addres = addres.substring(0,idx);
            f.setAdres(addres);
          } else {
            f.setAdres(addres);
          }
//          log.info("e info = {}", e);
//          log.info("f info = {}", f);
          elist.add(e);
          flist.add(f);


        }
      }
    }
    // 중복 제거를 위한 Set
    Set<PEvent> seteList = new HashSet<PEvent>(elist);
    Set<PFacility> setfList = new HashSet<PFacility>(flist);

         log.info("list ={}",seteList);
         log.info("================");
         log.info("list ={}",setfList);

    model.addAttribute("elist", seteList);
    model.addAttribute("flist", setfList);

    return "admin/post/maplist";
  }
}
