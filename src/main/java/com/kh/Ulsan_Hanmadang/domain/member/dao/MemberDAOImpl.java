package com.kh.Ulsan_Hanmadang.domain.member.dao;

import com.kh.Ulsan_Hanmadang.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor // final 멤버필드를 매개값으로 하는 생성자를 자동 생성한다.
public class MemberDAOImpl implements MemberDAO{

  private final JdbcTemplate jt;

  /**
   * 가입
   *
   * @param member 가입정보
   * @return 가입건수
   */
  @Override
  public Member insert(Member member) {
    int result = 0;
    StringBuffer sql = new StringBuffer();
    sql.append("insert into member ( member_id, email, password, name, nickname, phone, birthday, ");
    sql.append("                    sms_service, email_service, gubun) ");
    sql.append("values(member_member_id_seq.nextval,?,?,?,?,?,?,?,?,?) ");


    //SQL실행
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator() {
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

        PreparedStatement pstmt = con.prepareStatement(
                sql.toString(),
                new String[] {"member_id"}    // keyHolder에 담을 테이블의 컬럼명을 지정
        );
        pstmt.setString(1,member.getEmail());
        pstmt.setString(2,member.getPassword());
        pstmt.setString(3,member.getName());
        pstmt.setString(4,member.getNickname());
        pstmt.setString(5,member.getPhone());

        pstmt.setDate(6,member.getBirthday());
        pstmt.setBoolean(7,member.getSms_service());
        pstmt.setBoolean(8,member.getEmail_service());
        pstmt.setString(9,member.getGubun());

        return pstmt;
      }
    },keyHolder);

    long member_id = keyHolder.getKey().longValue();
    log.info("신규회원등록={} 후 member_id반환값={}",member, keyHolder.getKey());

    return findMemberById(member_id);
  }


  /**
   * 조회 by 회원아이디
   *
   * @param email 회원아이디
   * @return 회원정보
   */
  @Override
  public Member findMemberByEmail(String email) {
    StringBuffer sql = new StringBuffer();

    sql.append("select * from member ");
    sql.append(" where email = ? ");

    Member findedMember = null;
    try {
      //BeanPropertyRowMapper는 매핑되는 자바클래스에 디폴트생성자 필수!
      findedMember = jt.queryForObject(sql.toString(), new BeanPropertyRowMapper<>(Member.class),email);
    } catch (DataAccessException e) {
      log.info("찾고자하는 회원이 없습니다!={}",email);
    }
    log.info("DAO find member_id = {}", findedMember);

    return findedMember;
  }
  /**
   * 조회 by 내부관리 아이디
   *
   * @param member_id 내부관리 아이디
   * @return 회원정보
   */
  @Override
  public Member findMemberById(Long member_id) {

    StringBuffer sql = new StringBuffer();
    sql.append("select * from member ");
    sql.append(" where member_id = ? ");

    Member member = jt.queryForObject(
            sql.toString(),
            new BeanPropertyRowMapper<>(Member.class),
            member_id
    );
    return member;
  }

  /**
   * 수정
   * 수정 가능한 정보 : nickname, phone, sms_service, email_service
   * @param member 수정할 정보
   * @return 수정건수
   */
  @Override
  public void update(Member member) {

    StringBuffer sql = new StringBuffer();
    sql.append("update member ");
    sql.append("   set nickname = ?, ");
    sql.append("       phone = ?, ");
    sql.append("       sms_service = ?, ");
    sql.append("       email_service = ?, ");
    sql.append("       udate = systimestamp ");
    sql.append(" where email = ? ");

    jt.update(sql.toString(), member.getNickname(), member.getPhone(), member.getSms_service(),
            member.getEmail_service(), member.getEmail());

    log.info("member={}", member);
  }

  /**
   * 패스워드 확인
   *
   * @param email 회원 내부관리용 아이디
   * @param password 입력할 비밀번호
   * @return
   */
  @Override
  public boolean isMember(String email, String password) {
    String sql = "select count(password) from member where email = ? and password = ? ";
    Integer rowCount = jt.queryForObject(sql, Integer.class, email, password);

    return ((rowCount == 1)) ? true : false;
  }

  /**
   * 탈퇴
   *
   * @param email 아이디
   * @return 삭제건수
   */
  @Override
  public void del(String email) {

    String sql = "delete from member where email = ? ";

    jt.update(sql, email);
  }

  /**
   * 신규 회원아이디(내부관리용) 생성
   *
   * @return 회원아이디
   */
  @Override
  public Long generateId() {
    String sql = "select member_member_id_seq.nextval from dual ";
    Long member_id = jt.queryForObject(sql, Long.class);
    return member_id;
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
    String sql = "select count(*) from member where email = ? and password = ? ";
    Integer rowCount = jt.queryForObject(sql, Integer.class, email, password);

    return (rowCount == 1) ? true : false;
  }

  /**
   * 이메일 중복체크
   *
   * @param email 이메일
   * @return 존재하면 true
   */
  @Override
  public Boolean dupChkOfMemberEmail(String email) {
    String sql = "select count(email) from member where email = ? ";
    Integer rowCount = jt.queryForObject(sql, Integer.class, email);
    return (rowCount == 1) ? true : false;
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
    String sql = "select count(nickname) from member where nickname = ? and not email in (?)  ";
    Integer rowCount = jt.queryForObject(sql, Integer.class, nickname, email);
    return (rowCount == 1) ? true : false;
  }

  /**
   * 로그인
   *
   * @param email 이메일
   * @param password 비밀번호
   * @return 회원
   */
  @Override
  public Optional<Member> login(String email, String password) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * ");
    sql.append("  from member ");
    sql.append(" where email = ? ");
    sql.append("   and password = ? ");

    try {
      Member member = jt.queryForObject(
              sql.toString(),
              new BeanPropertyRowMapper<>(Member.class),
              email,password
      );
      return Optional.of(member);
    } catch (DataAccessException e) {
      return Optional.empty();
    }
  }

  /**
   * 아이디 찾기
   * @param nickname 찾을 회원의 이름
   * @return
   */
  @Override
  public String findMyEmail(String nickname) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member where nickname = ? ");

    List<String> result = jt.query(
            sql.toString(),
            new RowMapper<String>() {
              @Override
              public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getNString("email");
              }
            },
            nickname
    );

    return (result.size() == 1) ? result.get(0) : null;

  }

  /**
   * 비밀번호 찾기
   * @param email 찾을 회원의 이메일
   * @return
   */
  @Override
  public String findMyPassword(String email) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member where email = ? ");

    List<String> result = jt.query(
            sql.toString(),
            new RowMapper<String>() {
              @Override
              public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getNString("password");
              }
            },
            email
    );

    return (result.size() == 1) ? result.get(0) : null;

  }



  /**
   * 전체조회
   * @return
   */
  @Override
  public List<Member> selectAll() {
    StringBuffer sql = new StringBuffer();

    sql.append("select * from member ");
    sql.append(" order by member_id desc ");

    List<Member> list = jt.query(
            sql.toString(),
            new BeanPropertyRowMapper<>(Member.class)
    );

    return list;
  }


  /**
   * 회원유무 체크
   * @param email
   * @return
   */
  @Override
  public boolean exitMember(String email) {

    String sql = "select count(email) from member where email = ? ";
    Integer count = jt.queryForObject(sql, Integer.class, email);

    return (count==1) ? true : false;
  }
}

