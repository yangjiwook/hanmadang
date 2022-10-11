package com.kh.Ulsan_Hanmadang.web;

import com.kh.Ulsan_Hanmadang.domain.ApiResponse;
import com.kh.Ulsan_Hanmadang.domain.post.AddReplyReq;
import com.kh.Ulsan_Hanmadang.domain.post.Reply;
import com.kh.Ulsan_Hanmadang.domain.post.svc.PostSVC;
import com.kh.Ulsan_Hanmadang.web.session.LoginMember;
import com.kh.Ulsan_Hanmadang.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostRestController {

  private final PostSVC postSVC;

  @PostMapping(value = "/reply")
  public ApiResponse<Object> addReply(
          @RequestBody AddReplyReq addReplyReq,
          HttpSession session
  ) {
//    log.info("ARRQ={}", addReplyReq);

    //세션 가져오기
    LoginMember loginMember = (LoginMember) session.getAttribute(SessionConst.LOGIN_MEMBER);
    //검증
//    addReplyReq -> Reply
    Reply reply = new Reply();
    BeanUtils.copyProperties(addReplyReq, reply, "postId");
    reply.setPostId(Long.valueOf(addReplyReq.getPostId()));
    reply.setPcategory(addReplyReq.getPcategory());
    reply.setRcontent(addReplyReq.getRcontent());
    //세션에서 이메일,별칭가져오기
    try {
      reply.setEmail(loginMember.getEmail());
      reply.setNickname(loginMember.getNickname());
    } catch (NullPointerException e) {
        log.info("이메일, 별칭을 가져올 수 없습니다.");
    }

    Long rid = postSVC.saveReply(reply);
//    log.info("rid={}", rid);

    return ApiResponse.createApiResMsg("00", "성공", rid);
  }

  //검증 오류 메세지
  private Map<String, String> getErrMsg(BindingResult bindingResult) {
    Map<String, String> errmsg = new HashMap<>();

    bindingResult.getAllErrors().stream().forEach(objectError -> {
      errmsg.put(objectError.getCodes()[0],objectError.getDefaultMessage());
    });

    return errmsg;
  }

  //조회
  @GetMapping("/reply/{rid}")
  public ApiResponse<Reply> findReplyById(@PathVariable("rid") Long rid) {
    Optional<Reply> findedReply = postSVC.findReplyById(rid);
    //응답메세지
    ApiResponse<Reply> response = null;
    if(findedReply.isPresent()){
      response =  ApiResponse.createApiResMsg("00", "성공", findedReply.get());
    }else{
      response =  ApiResponse.createApiResMsg("01", "찾고자 하는 상품이 없습니다.", null);
    }
    return response;
  }

  //수정
//  int updateReplyById(Long rid, String rcontent);
  @PatchMapping("/reply/{rid}")
  public ApiResponse<Object> edit(
          @PathVariable("rid") Long rid,
          @RequestBody String rcontent
  ) {
    Optional<Reply> findedReply = postSVC.findReplyById(rid);
    if (findedReply.isEmpty()) {
      return ApiResponse.createApiResMsg("01", "댓글을 찾을 수 없습니다.", null);
    }
    postSVC.updateReplyById(rid, rcontent);
    //응답메세지
    return ApiResponse.createApiResMsg("01", "성공", postSVC.findReplyById(rid).get());
  }

  //삭제
//  int deleteReplyById(Long rid);
  @DeleteMapping("/reply/{rid}")
  public ApiResponse<Reply> del(@PathVariable("rid") Long rid) {
    //검증
    Optional<Reply> findedReply = postSVC.findReplyById(rid);
    if(findedReply.isEmpty()){
      return ApiResponse.createApiResMsg("01","삭제 하고자 상품이 없습니다.", null);
    }
    //삭제
    postSVC.deleteReplyById(rid);
    //응답메세지
    return ApiResponse.createApiResMsg("00","성공", null);
  }
  //목록
//  List<Reply> findReplyByPostId(Long postId);
  @GetMapping("/reply/list/{pid}")
  public ApiResponse<List<Reply>> findAll(@PathVariable("pid") Long pid){
    List<Reply> list = postSVC.findReplyByPostId(pid);
    //api응답 메세지
    return ApiResponse.createApiResMsg("00","성공",list);
  }

  //이벤트 조회 by 날짜
  @GetMapping("/{date}/events")
  public ApiResponse<List<String>> findEventsByDate(@PathVariable("date") String date) {

    List<String> findedEvents = new ArrayList<>();

    findedEvents = postSVC.findAllEventByDate(date);
    log.info("fEvent={}", findedEvents);
    //api응답 메세지
    return ApiResponse.createApiResMsg("00","성공",findedEvents);
  }
}
