package com.kh.Ulsan_Hanmadang.web;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.svc.MemberSVC;
import com.kh.Ulsan_Hanmadang.web.form.member.*;
import com.kh.Ulsan_Hanmadang.web.session.LoginMember;
import com.kh.Ulsan_Hanmadang.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  final MemberSVC memberSVC;


  //성별
  @ModelAttribute("gubun")
  public Gubun[] gubuns(){

    return Gubun.values();  //[F0101, F0102]
  }


  //문자열로 Enum객체에서 상수요소 찾아오기
  private Gubun getGubun(String gender) {
    Gubun finded = null;
    for(Gubun g : Gubun.values()){
      //log.info("구분 = {}", g);
      if(g.getDescription().equals(gender)){
        finded = Gubun.valueOf(g.name());
        break;
      }
    }
    return finded;
  }


  //콤마를 구분자로하는 문자열을 문자열 요소로갖는 리스트로 변환
  private List<String> stringToList(String str) {
    String[] array = str.split(",");
    //log.info("array={}", array.length);
    List<String> list = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      list.add(array[i]);
    }
    return list;
  }




  // 회원가입 GET
  @GetMapping("/sign_in")
  public String add(@ModelAttribute AddForm addForm){

    return "member/sign_in";
  }
  // 회원가입 처리 POST
  @PostMapping("/sign_in")
  public String add(@Valid @ModelAttribute AddForm addForm,
                    BindingResult bindingResult){


    //검증
    if(bindingResult.hasErrors()){
      //log.info("errors={}",bindingResult);
      return "member/sign_in";
    }
    //회원아이디 중복체크
    Boolean isExistEmail = memberSVC.dupChkOfMemberEmail(addForm.getEmail());
    if(isExistEmail){
      bindingResult.rejectValue("email","dup.email", "동일한 이메일이 존재합니다.");
      return "member/sign_in";
    }

    //닉네임 중복체크
    Boolean isExistNickName = memberSVC.dupChkOfMemberNickname(addForm.getNickname(), addForm.getEmail());
    if(isExistNickName){
      bindingResult.rejectValue("nickname","dup.nickname", "동일한 닉네임이 존재합니다.");
      return "member/sign_in";
    }
    //회원등록 아이디 이름 닉네임 폰 비밀번호 생년월일
    Member member = new Member();
    member.setEmail(addForm.getEmail());
    member.setPassword(addForm.getPassword());
    member.setName(addForm.getName());
    member.setNickname(addForm.getNickname());
    member.setPhone(addForm.getPhone());
    member.setBirthday(addForm.getBirthday());
    member.setGubun(addForm.getGubun().getDescription());
    member.setSms_service(addForm.getSms_service());
    member.setEmail_service(addForm.getEmail_service());
//    log.info("회원가입 = {} {} {} ", member.getBirthday(), member.getSms_service(), member);

    Member insertedMember = memberSVC.insert(member);

//    log.info("new Member = {}", member);

    return "redirect:/login";
  }


  // 내정보
  @GetMapping("/{email}/info")
  public String info(@PathVariable String email, Model model) {

    Member member = memberSVC.findMemberByEmail(email);

    DetailForm detailForm = new DetailForm();
    detailForm.setEmail(member.getEmail());
    detailForm.setName(member.getName());
    detailForm.setNickname(member.getNickname());
    detailForm.setPhone(member.getPhone());
    detailForm.setBirthday(member.getBirthday());
    detailForm.setSms_service(member.getSms_service());
    detailForm.setEmail_service(member.getEmail_service());
    detailForm.setGubun(getGubun(member.getGubun()));
    detailForm.setCdate(member.getCdate());
    detailForm.setUdate(member.getUdate());

    //log.info("내정보 = {}",detailForm);

    model.addAttribute("form", detailForm);

    return "member/my_info";
  }

  // 비밀번호 체크 GET [수정화면 이전 비밀번호 확인]
  @GetMapping("/pwCheck")
  public String pwChk(Model model){
    model.addAttribute("form" , new PasswordForm());
    return "member/my_info_update_pwd";
  }

  // 비밀번호 체크 POST [수정화면 이전 비밀번호 확인]
  @PostMapping("/pwCheck")
  public String pwChk(@Valid @ModelAttribute("form") PasswordForm passwordForm,
                      BindingResult bindingResult,
                      HttpServletRequest request, Model model) {


    HttpSession session = request.getSession(false);
    LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);

    //검증
    if(bindingResult.hasErrors()){
      //log.info("errors={}",bindingResult);
      return "member/my_info_update_pwd";
    }

    //비밀번호 맞는지 확인
    Boolean isExistPassword = memberSVC.isPassword(loginMember.getEmail(), passwordForm.getPassword());
//    log.info("id={}, password={}, isExist={}",loginMember.getId(), passwordForm.getPassword(), isExistPassword);
    if(!isExistPassword){
      bindingResult.rejectValue("password","check.password", "비밀번호가 일치하지 않습니다.");
      return "member/my_info_update_pwd";
    }

    //model.addAttribute("editForm", new EditForm());
    return "redirect:/members/"+loginMember.getEmail()+"/infoUpdate";  //회원정보 수정화면 이동
  }



  //수정화면 Get
  @GetMapping("/{email}/infoUpdate")
  public String updateInfo(@PathVariable("email") String email, Model model)  {

    Member member = memberSVC.findMemberByEmail(email);

    EditForm editForm = new EditForm();
    editForm.setEmail(member.getEmail());
    editForm.setName(member.getName());
    editForm.setNickname(member.getNickname());
    editForm.setPhone(member.getPhone());
    editForm.setBirthday(member.getBirthday());
    editForm.setSms_service(member.getSms_service());
    editForm.setEmail_service(member.getEmail_service());

    //log.info("수정폼={}",editForm);

    model.addAttribute("editForm", editForm);

    return "member/my_info_update";
  }

  //수정화면 Post
  @PostMapping("/infoUpdate")
  public String updateInfoPost(@Valid @ModelAttribute("editForm") EditForm editForm,
                               BindingResult bindingResult, HttpServletRequest request,
                               RedirectAttributes redirectAttributes){

    //1)유효성체크
    if(bindingResult.hasErrors()){
      log.info("bindingResult={}",bindingResult);
      return "/member/my_info_update";
    }

    //닉네임 중복체크 -- 본앙아이디 제외하고 체크
    Boolean isExistNickName = memberSVC.dupChkOfMemberNickname(editForm.getNickname(), editForm.getEmail());
    if(isExistNickName){
      bindingResult.rejectValue("nickname","dup.nickname", "동일한 닉네임이 존재합니다.");
      return "member/my_info_update";
    }


    Member member = new Member();

    member.setEmail(editForm.getEmail());
    member.setName(editForm.getName());
    member.setNickname(editForm.getNickname());
    member.setPhone(editForm.getPhone());
    member.setBirthday(editForm.getBirthday());
    member.setSms_service(editForm.getSms_service());
    member.setEmail_service(editForm.getEmail_service());

    //수정
    memberSVC.update(member);
    //조회
    Member updatedMember = memberSVC.findMemberByEmail(editForm.getEmail());

    LoginMember loginMember = new LoginMember(updatedMember.getMember_id(), updatedMember.getEmail(),updatedMember.getPassword(),
        updatedMember.getName(),updatedMember.getNickname(), updatedMember.getPhone(), updatedMember.getBirthday(),
        updatedMember.getSms_service(), updatedMember.getEmail_service(),updatedMember.getGubun(), updatedMember.getCdate(),updatedMember.getUdate());


    //request.getSession(true) : 세션정보가 있으면 가져오고 없으면 세션을 많듬
    HttpSession session = request.getSession(false);
    session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);


//    session.setAttribute(SessionConst.LOGIN_MEMBER, nowSession);
//    log.info("수정 폼 = {}",member);
//    log.info("수정 세션 = {}",session.getAttribute(SessionConst.LOGIN_MEMBER));


    redirectAttributes.addAttribute("email", member.getEmail());
    return "redirect:/members/{email}/info"; //회원 수정화면
  }


  //회원탈퇴
  @GetMapping("/{email}/leave")
  public String leaveForm(@ModelAttribute LeaveForm leaveForm){
    //log.info("leave 호출됨!");
    return "member/leave_member";
  }

  @PostMapping("/leave")
  public String leaveForm(
      @Valid @ModelAttribute LeaveForm leaveForm,
      BindingResult bindingResult,
      HttpSession session){

    //log.info("out 호출됨");
    //1)유효성체크
    if(bindingResult.hasErrors()){
      //log.info("bindingResult={}",bindingResult);
      return "/member/leave_member";
    }

    //3) 비밀번호 일치하는지 체크
    if(!memberSVC.isMember(leaveForm.getEmail(), leaveForm.getPassword())){
      bindingResult.rejectValue("password","check.password", "비밀번호가 일치하지 않습니다.");
      //log.info("bindingResult={}", bindingResult);
      return "member/leave_member";
    }

    memberSVC.del(leaveForm.getEmail());

    if(session != null){
      session.invalidate();
    }

    return "redirect:/members/leaveClear";
  }
  // 회원탈퇴 완료
  @GetMapping("/leaveClear")
  public String leaveClear() {

    return "member/leaveClear";
  }

  // 아이디 찾기
  @GetMapping("/findByEmail")
  public String findByEmail() {

    return "member/findEmail";
  }

  // 비밀번호 찾기
  @GetMapping("/findByPassword")
  public String findByPassword() {

    return "member/findPassword";
  }
}
