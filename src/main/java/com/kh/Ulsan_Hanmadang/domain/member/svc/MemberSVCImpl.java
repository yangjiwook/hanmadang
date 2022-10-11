package com.kh.Ulsan_Hanmadang.domain.member.svc;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import com.kh.Ulsan_Hanmadang.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  /**
   * 가입
   *
   * @param member 가입정보
   * @return 회원아이디
   */
  @Override
  public Member insert(Member member) {
    return memberDAO.insert(member);
  }

  /**
   * 조회 by 회원아이디
   *
   * @param email 회원아이디
   * @return 회원정보
   */
  @Override
  public Member findMemberByEmail(String email) {
    return memberDAO.findMemberByEmail(email);
  }

  @Override
  public Member findMemberById(Long member_id) {
    return memberDAO.findMemberById(member_id);
  }

  /**
   * 수정
   * @param member   수정할 정보
   * @return 수정건수
   */
  @Override
  public void update(Member member) {
    memberDAO.update(member);
  }

  /**
   * 패스워드 확인
   *
   * @param email  회원 아이디
   * @param password 입력할 비밀번호
   * @return
   */
  @Override
  public boolean isMember(String email, String password) {
    boolean cnt = memberDAO.isMember(email,password);
    return cnt;
  }

  /**
   * 탈퇴
   *
   * @param email 아이디
   * @return 삭제건수
   */
  @Override
  public void del(String email) {
    memberDAO.del(email);
  }

  /**
   * 비밀번호 체크
   *
   * @param email
   * @param password
   * @return
   */
  @Override
  public boolean isPassword(String email, String password) {
    return memberDAO.isPassword(email, password);
  }

  /**
   * 이메일 중복체크
   *
   * @param email 이메일
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfMemberEmail(String email) {

    return memberDAO.dupChkOfMemberEmail(email);
  }

  /**
   * 닉네임 중복체크
   *
   * @param nickname 닉네임
   * @param email 이메일
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfMemberNickname(String nickname, String email) {
    return memberDAO.dupChkOfMemberNickname(nickname, email);
  }

  /**
   * 로그인
   *
   * @param email    이메일
   * @param password 비밀번호
   * @return 회원
   */
  @Override
  public Optional<Member> login(String email, String password) {
    return  memberDAO.login(email, password);
  }

  /**
   * 아이디 찾기
   *
   * @param nickname  찾을 회원의 닉네임
   * @return
   */
  @Override
  public String findMyEmail(String nickname) {
    return memberDAO.findMyEmail(nickname);
  }

  /**
   * 비밀번호 찾기
   *
   * @param email 찾을 회원의 이메일
   * @return
   */
  @Override
  public String findMyPassword(String email) {
    return memberDAO.findMyPassword(email);
  }


  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Member> findAll() {
    return memberDAO.selectAll();
  }

  /**
   * 회원유무 체크
   * @param email
   * @return
   */
  public boolean existMember(String email) {
    return memberDAO.exitMember(email);
  }
}
