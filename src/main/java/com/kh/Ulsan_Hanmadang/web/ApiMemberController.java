package com.kh.Ulsan_Hanmadang.web;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.svc.MemberSVC;
import com.kh.Ulsan_Hanmadang.web.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiMemberController {

  private final MemberSVC memberSVC;

  @ResponseBody //http응답 메세지 바디에 직접 쓰기
  //(반환타입이 객체이면 java객체=>json포맷 문자열로 변환후)
  @GetMapping("/api/members")
  public ApiResult<List<Member>> members(){
    List<Member> list = memberSVC.findAll();
    ApiResult<List<Member>> result = new ApiResult<>("00","success",list);
    return result;
  }

  @ResponseBody
  @GetMapping("/api/member")
  public ApiResult<Member> member(@RequestParam String email){
    Member member = memberSVC.findMemberByEmail(email);
    ApiResult<Member> result = new ApiResult<>("00","success",member);
    return result;
  }

  @ResponseBody
  @GetMapping("/api/members/{email}/exist")
  public ApiResult<Member> existMember(@PathVariable String email){

    boolean existMember = memberSVC.existMember(email);

    if(existMember){
      return new ApiResult("00","success","OK");
    }else{
      return new ApiResult("99","fail","NOK");
    }
  }
  @ResponseBody
  @PutMapping("/api/members/email/find")
  public ApiResult<String> findEmailByNickname(
          @RequestBody String nickname
  ){

    log.info("nickname={}",nickname);
    ApiResult<String> result = null;

    String email = memberSVC.findMyEmail(nickname);

    //StringUtils.isEmpty : null또는 ""문자열 인지 체크
    //if(email == null || email.equals(""))
    if(!StringUtils.isEmpty(email)) {
      result = new ApiResult<>("00", "success", email);
    }else{
      result = new ApiResult<>("99", "fail", "찾고자하는 아이디가 없습니다.");
    }
    return result;
  }


  @ResponseBody
  @PutMapping("/api/members/password/find")
  public ApiResult<String> findPasswordByEmail(
          @RequestBody String email
  ){

    log.info("nickname={}",email);
    ApiResult<String> result = null;

    String password = memberSVC.findMyPassword(email);

    //StringUtils.isEmpty : null또는 ""문자열 인지 체크
    //if(email == null || email.equals(""))
    if(!StringUtils.isEmpty(password)) {
      result = new ApiResult<>("00", "success", password);
    }else{
      result = new ApiResult<>("99", "fail", "찾고자하는 아이디가 없습니다.");
    }
    return result;
  }
//
//  //프로파일 이미지 조회
//  @GetMapping("/api/members/{memberId}/pfimg")
//  public ResponseEntity<byte[]> findPicOfProfile(@PathVariable Long memberId){
//
//    byte[] picOfProfile = memberSVC.findPicOfProfile(memberId);
//    return ResponseEntity.ok()  //응답코드 200
//        .body(picOfProfile);
//  }
//
//  //프로파일 이미지 수정
//  @PutMapping("/api/members/{memberId}/pfimg")
//  public ResponseEntity<ApiResult<String>> upfdatePicOfProfile(
//      @PathVariable Long memberId,
//      @RequestBody(required = false) byte[] file
//  ) throws IOException {
//
//    int affectedRow = memberSVC.updatePicOfProfile(memberId, file);
//    ApiResult<String> result = null;
//    if(affectedRow == 1) {
//      result = new ApiResult<>("00", "success", "수정");
//    }else{
//      result = new ApiResult<>("99", "fail", "실패");
//    }
//    return ResponseEntity.ok()  //응답코드 200
//        .body(result);
//  }
//
//  //프로파일 별칭 수정
//  @ResponseBody
//  @PutMapping("/api/members/{memberId}/pfnick")
//  public ResponseEntity<ApiResult<String>> upfdateNicknameOfProfile(
//      @PathVariable Long memberId,
//      @RequestBody String nicknameOfProfile,
//      HttpSession session
//  ){
//    log.info("nicknameOfProfile={}",nicknameOfProfile);
//    int affectedRow =memberSVC.updateNickNameOfProfile(memberId,nicknameOfProfile);
//    ApiResult<String> result = null;
//    if(affectedRow == 1) {
//      result = new ApiResult<>("00", "success", "수정");
//      LoginMember loginMember = (LoginMember)session.getAttribute(SessionConst.LOGIN_MEMBER);
//      loginMember.setNickname(nicknameOfProfile);
//      session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
//    }else{
//      result = new ApiResult<>("99", "fail", "실패");
//    }
//    return ResponseEntity.ok()  //응답코드 200
//        .body(result);
//  }
}
