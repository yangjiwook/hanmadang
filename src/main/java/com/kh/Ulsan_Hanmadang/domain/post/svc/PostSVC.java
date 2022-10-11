package com.kh.Ulsan_Hanmadang.domain.post.svc;

import com.kh.Ulsan_Hanmadang.domain.EventInfo;
import com.kh.Ulsan_Hanmadang.domain.FacInfo;
import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PromotionHome;
import com.kh.Ulsan_Hanmadang.domain.post.Post;
import com.kh.Ulsan_Hanmadang.domain.post.Promotion;
import com.kh.Ulsan_Hanmadang.domain.post.Reply;
import com.kh.Ulsan_Hanmadang.domain.post.dao.PostFilterCondition;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PostSVC {

  /**
   * 원글작성
   * @param post
   * @return 게시글 번호
   */
  Long saveOrigin(Post post);

  /**
   * 원글작성-첨부파일 있는경우
   * @param post
   * @param files 첨파일
   * @return 게시글 번호
   */
  Long saveOrigin(Post post, List<MultipartFile> files);

  /**
   * 목록
   * @return
   */
  List<Post> findAll();
  List<Post>  findAll(int startRec, int endRec);
  List<Post>  findAll(String category, int startRec, int endRec);
  /**
   * 검색
   * @param filterCondition 분류,시작레코드번호,종료레코드번호,검색유형,검색어
   * @return
   */
  List<Post>  findAll(PostFilterCondition filterCondition);

  List<EventInfo> findAllEvents(PostFilterCondition filterCondition);
  /**
   * 목록
   * @return 이벤트 정보
   */
  List<EventInfo> findAllEvents(int startRec, int endRec);
//  List<EventInfo> findAllEventByDate(PostFilterCondition filterCondition);
//  List<String> findAllEventByDate(PostFilterCondition filterCondition);
  List<String> findAllEventByDate(String date);
  /**
   * 상세 조회
   * @param id 게시글번호
   * @return
   */
  Post findByPostId(Long id);

  EventInfo findByEventId(Long id);
  FacInfo findByFacId(String id);

  /**
   * 삭제
   * @param id 게시글번호
   * @return 삭제건수
   */
  int deleteByPostId(Long id);

  /**
   * 수정
   * @param id 게시글 번호
   * @param post 수정내용
   * @return 수정건수
   */
  int updateByPostId(Long id, Post post);

  /**
   * 수정-첨부
   * @param id 게시글 번호
   * @param post 수정내용
   * @param files 첨부파일
   * @return 수정건수
   */
  int updateByPostId(Long id, Post post, List<MultipartFile> files);

  /**
   * 답글작성
   * @param reply 답글
   * @return 답글번호
   */
  Long saveReply(Reply reply);
//  Long saveReply(Reply reply, List<MultipartFile> files);
  /**
   * 댓글 수정
   * @param rid 댓글 번호
   * @return 댓글 내용
   */
  int updateReplyById(Long rid, String rcontent);
  /**
   * 댓글 조회
   * @param rid 댓글 번호
   * @return 댓글 정보
   */
  Optional<Reply> findReplyById(Long rid);

  /**
   * 댓글 삭제
   *
   * @param rid 댓글 번호
   * @return 삭제 건수
   */
  int deleteReplyById(Long rid);
  /**
   * 댓글 목록
   * @param postId 부모글
   * @return 댓글 그룹
   */
  List<Reply> findReplyByPostId(Long postId);
  /**
   * 홈화면에 디스플레이할 공연이미지 목록
   * @param date 현재 시간
   * @return 이미지 목록
   */
  List<PEvent> getDisplyEvents(String date);

  /**
   * 디스플레이될 공연수
   * @param date 현재 시간
   * @return 목록수
   */
  int getDisplyEventCount(String date);

  /**
   * 메인화면 노출 후기 정보
   * @return 후기 목록
   */
  List<Post> getNewReview();

  /**
   * 메인화면 노출 홍보 정보
   * @param date 현재 시간
   * @return
   */
//  List<Post> getNewPromotion(String date);
  List<Post> getNewPromotion();
  /**
   * 홍보 게시글 조회
   * @param pid 게시글번호
   * @return 홍보정보
   */
  Promotion getPromotionInfoById(Long pid);
  int totalCount();
  int totalCount(String pcategory);
  int totalCount(PostFilterCondition filterCondition);
  int totalPEventCount();

  /**
   * 내가 쓴 글
   * @param email 아이디
   * @return
   */
  List<Post> myPost(String email);

  Promotion savePromotionData(Promotion promotion);
  Promotion findByPromotionId(Long pid);


  /**
   * 내가 쓴 댓글
   * @param email 아이디
   * @return
   */
  List<Reply> myComment(String email);


  /**
   * 홈화면에 디스플레이할 홍보이미지 목록
   * @param date 현재 시간
   * @return 이미지 목록
   */
  List<PromotionHome> getDisplyProms(String date);
}