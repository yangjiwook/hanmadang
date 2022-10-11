package com.kh.Ulsan_Hanmadang.web;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.svc.MemberSVC;
import com.kh.Ulsan_Hanmadang.web.form.member.DetailForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

  private final MemberSVC memberSVC;

//  @Autowired
//  public AdminController(MemberSVC memberSVC){
//    this.memberSVC = memberSVC;
//  }

//  //성별
//  @ModelAttribute("gender")
//  public Gender[] genter(){
//
//    return Gender.values();  //[MALE, FEMALE]
//  }
//
//  //취미
//  @ModelAttribute("hobbys")
//  public Map<String,String> hobbys(){
//    Map<String,String> hobbys = new HashMap<>();
//    hobbys.put("reading", "독서");
//    hobbys.put("swim", "수영");
//    hobbys.put("climing", "등산");
//    hobbys.put("golf", "골프");
//    return hobbys;
//  }
//
//  //지역
//  @ModelAttribute("regions")
//  public List<String> regions(){
//    List<String> regions = new ArrayList<>();
//    regions.add("서울");
//    regions.add("부산");
//    regions.add("울산");
//    regions.add("대구");
//    regions.add("제주");
//    return regions;
//  }

  //공공데이터 요청 화면
  @GetMapping("/api")
  public String ApiForm() {

    return "/admin/apiForm";
  }


  //관리자 홈
  @GetMapping
  public String home(){
    return "admin/home";
  }

  //회원전체조회
  @GetMapping("/members")
  public String members(Model model){

    List<Member> memberAll = memberSVC.findAll();
    model.addAttribute("memberAll", memberAll);

    return "admin/member/members";
  }

  @GetMapping("/apimembers")
  public String apiMembers(){

    return "admin/member/apimembers";
  }

  //회원개별조회
  @GetMapping("/members/{email}")
  public String member(
          @PathVariable("email") String email,
          Model model){

    Member member = memberSVC.findMemberByEmail(email);
    log.info("member={}", member);

    DetailForm detailForm = new DetailForm();
    detailForm.setEmail(member.getEmail());
    detailForm.setNickname(member.getNickname());
//    detailForm.setGender(getGender(member.getGender()));
//    detailForm.setHobby(stringToList(member.getHobby()));
//    detailForm.setRegion(member.getRegion());
    log.info("detailForm={}", detailForm);
    model.addAttribute("detailForm", detailForm);

    return "admin/member/member";
  }
//
//  //회원탈퇴
//  @GetMapping("/members/{email}/del")
//  public String out(
//      @PathVariable("email") String email){
//
//    memberSVC.out(email);
//
//    return "redirect:/admin/members";
//  }


//
//  //문자열로 Enum객체에서 상수요소 찾아오기
//  private Gender getGender(String gender) {
//    Gender finded = null;
//    for(Gender g : Gender.values()){
//      if(g.getDescription().equals(gender)){
//        finded = Gender.valueOf(g.name());
//        break;
//      }
//    }
//    return finded;
//  }

  //콤마를 구분자로하는 문자열을 문자열 요소로갖는 리스트로 변환
  private List<String> stringToList(String str) {
    String[] array = str.split(",");
    log.info("array={}", array.length);
    List<String> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
    return list;
  }


}

