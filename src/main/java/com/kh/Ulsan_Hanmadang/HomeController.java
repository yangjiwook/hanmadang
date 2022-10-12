package com.kh.Ulsan_Hanmadang;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;
import com.kh.Ulsan_Hanmadang.domain.PromotionHome;
import com.kh.Ulsan_Hanmadang.domain.admin.svc.AdminPostSVC;
import com.kh.Ulsan_Hanmadang.domain.common.code.CodeDAO;
import com.kh.Ulsan_Hanmadang.domain.common.paging.FindCriteria;
import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.svc.MemberSVC;
import com.kh.Ulsan_Hanmadang.domain.post.Post;
import com.kh.Ulsan_Hanmadang.domain.post.svc.PostSVC;
import com.kh.Ulsan_Hanmadang.web.Code;
import com.kh.Ulsan_Hanmadang.web.form.member.LoginForm;
import com.kh.Ulsan_Hanmadang.web.session.LoginMember;
import com.kh.Ulsan_Hanmadang.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

  final MemberSVC memberSVC;
  private final PostSVC postSVC;
  private final CodeDAO codeDAO;
  private final AdminPostSVC adminPostSVC;
  String test_date = "2022.09.25";
  String beforeLoginUrl;

  @Autowired
  @Qualifier("fc6") //동일한 타입의 객체가 여러개있을때 빈이름을 명시적으로 지정해서 주입받을때
  private FindCriteria fc;

  //게시판 코드,디코드 가져오기
  @ModelAttribute("classifier")
  public List<Code> classifier(){
    return codeDAO.code("B01");
  }

  @GetMapping
  private String home(Model model) {
    int cutIndex = 20;
    List<PEvent> elist = postSVC.getDisplyEvents(test_date);
    List<PromotionHome> plist = postSVC.getDisplyProms(test_date);
    List<Post> newReviewList = postSVC.getNewReview();
//    List<Post> newPromList = postSVC.getNewPromotion(test_date);
    List<Post> newPromList = postSVC.getNewPromotion();

    List<PEvent> eventList =adminPostSVC.pEventList();
    List<PFacility> facilityList = adminPostSVC.pFacilityList();

    for (Post review : newReviewList) {
      if(review.getTitle().length() > cutIndex){
//        log.info("tlent over={}", post.getTitle());
        review.setTitle(review.getTitle().substring(0, cutIndex) + "...");
//        log.info("tlength over = {}", post.getTitle());
      }
    }
    for (Post prom : newPromList) {
      if(prom.getTitle().length() > cutIndex) {
        prom.setTitle(prom.getTitle().substring(0, cutIndex) + "...");
      }
    }
    model.addAttribute("elist", elist);
    model.addAttribute("plist", plist);
    model.addAttribute("newReviewList", newReviewList);
    model.addAttribute("newPromList", newPromList);

    // 현재 공연중인 공연장만 담기
    List<PFacility> fac_chk = new ArrayList<>();

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
          fac_chk.add(f);
        }
      }
    }
    Set<PFacility> setfList = new HashSet<PFacility>(fac_chk);
    log.info("list ={}",setfList);

    model.addAttribute("flist", setfList);

    return "home";
  }

  //로그인 화면
  @GetMapping("/login")
  public String loginForm(@ModelAttribute("form") LoginForm loginForm,
                          HttpServletRequest request){

    beforeLoginUrl = request.getHeader("referer");
    log.info("origin 리퀘스트 = {}", beforeLoginUrl);

    int idx = beforeLoginUrl.indexOf("8"); // 9080/ 에서 8을 확인
    beforeLoginUrl = beforeLoginUrl.substring(idx+3);
    log.info("리퀘스트 = {}", beforeLoginUrl);

    return "member/login";
  }


  //로그인 처리
  @PostMapping("/login")
  public String login(@Valid @ModelAttribute("form") LoginForm loginForm,
                      BindingResult bindingResult,
                      HttpServletRequest request,
                      @RequestParam(value = "requestURI",required = false,defaultValue = "/") String requestURI){

    //기본 검증
    if (bindingResult.hasErrors()) {
      //log.info("bindingResult={}",bindingResult);
      return "member/login";
    }

    //회원유무
    Optional<Member> member = memberSVC.login(loginForm.getEmail(), loginForm.getPassword());
    //log.info("member={}", member);
    if(member.isEmpty()){
      bindingResult.reject("LoginForm.login","회원정보가 없습니다.");
      return "member/login";
    }

    //회원인경우
    Member findedMember = member.get();

    //세션에 회원정보 저장
    LoginMember loginMember = new LoginMember(findedMember.getMember_id(), findedMember.getEmail(),findedMember.getPassword(),
            findedMember.getName(),findedMember.getNickname(), findedMember.getPhone(), findedMember.getBirthday(),
            findedMember.getSms_service(), findedMember.getEmail_service(),findedMember.getGubun(), findedMember.getCdate(),findedMember.getUdate());

    //request.getSession(true) : 세션정보가 있으면 가져오고 없으면 세션을 많듬
    HttpSession session = request.getSession(true);
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

    String URI = request.getQueryString();

    if(URI == null) {
      // 이전 페이지가 뭐 있을때
      if(beforeLoginUrl.length() > 1){

        if(beforeLoginUrl.contains("members/sign_in")){
          // 회원가입하고 로그인창일때는 바로 홈화면으로 가도록
          return "redirect:/";
        }
        else if(beforeLoginUrl.contains("members/login")){
          // 로그인창일때는 바로 홈화면으로 가도록
          return "redirect:/";
        }


        return "redirect:/"+beforeLoginUrl;
      }

      return "redirect:/";
    }

    if(URI == null) {
      // 바로 로그인 눌렀을때
//      log.info("Login URI ={}",URI);

      if(beforeLoginUrl.length() > 1){
//        log.info("before ={}",beforeLoginUrl);

        return "redirect:/"+beforeLoginUrl;
      }

      return "redirect:/";
    }
    // 게시판 열람은 비로그인도 사용가능함. [예외 상황용 코드]
//    else if( URI.contains("/post/list")){
//      // 게시판 눌렀을때
//      URI = URI.substring(URI.lastIndexOf("=")+1);
//      if(URI.contains("&")){
//        URI = URI.replace("&","?");
//
//        if(URI.contains("%3D")){
//          URI = URI.replace("%3D","=");
//        }
//      }
//      log.info("URI2 str ={}",URI);
//
//      return "redirect:"+URI;
//    }
    else if(URI.contains("/members//info")){
      // 내 정보 눌렀을때
      return "redirect:/members/"+loginMember.getEmail()+"/info";
    }
    else {
      // 예외 페이지는 우선 홈으로
//      log.info("URI 예외 ={}",URI);

      return "redirect:/";
    }
  }



  //로그아웃

  //로그아웃
  @GetMapping("/logout")
  public String logout(HttpServletRequest request){

    beforeLoginUrl = request.getHeader("referer");
    int idx = beforeLoginUrl.indexOf("8"); // 9080/ 에서 8을 확인
    beforeLoginUrl = beforeLoginUrl.substring(idx+3);
//    log.info("리퀘스트 = {}", beforeLoginUrl);

    if(beforeLoginUrl.length() > 1){
//        log.info("before ={}",beforeLoginUrl);
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
      if(beforeLoginUrl.contains("members/") || beforeLoginUrl.contains("history/")){
        return "redirect:/";
      }

      return "redirect:/"+beforeLoginUrl;
    }

    //request.getSession(false) : 세션정보가 있으면 가져오고 없으면 세션을 만들지 않음
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/"; //초기화면 이동
  }
}