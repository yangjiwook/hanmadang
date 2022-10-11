-------
--코드
-------
drop table uploadfile;
drop table good;
drop table reply;
drop table p_event;
drop table p_facility;
drop table promotion;
drop table post;
drop table member;
drop table code;

create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    discript    clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
--alter table code drop constraint post_pcode_id_fk;
-- alter table code add constraint post_pcode_id_fk foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--샘플데이터 of code
insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0101','공연전시','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0102','홍보','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0103','후기','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('B0104','문화지도','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01','회원구분',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0101','일반','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0102','홍보','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A1','관리자1','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A2','관리자2','M01','Y');

-------------------------------------------------------------------------------------------

drop sequence member_member_id_seq;

create sequence member_member_id_seq;

create table member (
  member_id      number(8),      --기본키
  email         varchar(30),    --(이메일이 아이디)
  password         varchar2(20),   -- nn
  name            varchar2 (10),  -- 유니크 nn
  nickname         varchar2 (20),  -- 유니크 nn
  phone         varchar2 (12),  -- 유니크 nn
  birthday         date,           -- nn
  sms_service      number(1),
  email_service   number(1),
  gubun           varchar2(11)   default 'M0101', --회원구분 (일반,홍보,관리자..) M0102 홍보
  cdate            timestamp default systimestamp,
  udate            timestamp default systimestamp
);


alter table member add constraint member_member_id_pk primary key(member_id);

alter table member add constraint member_gubun_fk foreign key(gubun) references code(code_id);

alter table member modify email constraint member_email_nn not null;
alter table member modify nickname constraint member_nickname_nn not null;
alter table member modify password constraint member_password_nn not null;
alter table member modify name constraint member_name_nn not null;
alter table member modify phone constraint member_phone_nn not null;
alter table member modify birthday constraint member_birthday_nn not null;

alter table member add constraint member_email_unique unique (email);
alter table member add constraint member_nickname_unique unique (nickname);



desc member;

select * from member;

------------ End Create Table MEMBER Query ------------
--......별칭,폰,생일,sns svc, email svc
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test1@kh.com', '1234','홍길동', '테스터1','01011112222','1999-01-01',1,1,'M0101');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test2@kh.com', '1234','홍길서', '테스터2','01011113333','2001-03-03',1,1,'M0102');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','홍길남','관리자1', '01022223333','2009-04-04',1,1,'M01A1');
insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','홍길북','관리자2', '01033334444','2010-05-05',1,1,'M01A2');

insert into member (member_id,email,password,name,nickname,phone,birthday,sms_service,email_service,gubun)
    values(member_member_id_seq.nextval, 'test1@test.com', '1234', '김일번','퍼스트맨','01011112222','1999-01-01',1,1,'M0101');

commit;

-------------------------------------------------------------------------------------------
-------
--게시판
-------

drop sequence post_post_id_seq;

--시퀀스
create sequence post_post_id_seq;

create table post(
    p_event_id  number(10),
    post_id      number(10),         --게시글 번호
    pcategory   varchar2(11),       --분류카테고리
    title       varchar2(150),      --제목
    email       varchar2(50),       --email
    nickname    varchar2(30),       --별칭
    hit         number(5) default 0,          --조회수
    pcontent    clob,               --본문
--    ppost_id     number(10),         --부모 게시글번호
--    pgroup      number(10),         --답글그룹
--    step        number(3) default 0,          --답글단계
--    pindent     number(3) default 0,          --답글들여쓰기
--    status      char(1),               --답글상태  (삭제: 'D', 임시저장: 'I')
    d_dat       date,
--    ad_start_day  date,
--    ad_end_day    date,
--    ent_fee     varchar2(10),
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);


--기본키
alter table post add Constraint post_post_id_pk primary key (post_id);

--외래키
--alter table post drop constraint post_bcategory_fk;
alter table post add constraint post_pcategory_fk foreign key(pcategory) references code(code_id);
-- alter table post add constraint post_ppost_id_fk foreign key(ppost_id) references post(post_id);
alter table post add constraint post_email_fk foreign key(email) references member(email);
--alter table post drop constraint post_email_fk;
--제약조건
alter table post modify pcategory constraint post_pcategory_nn not null;
alter table post modify title constraint post_title_nn not null;
alter table post modify email constraint post_email_nn not null;
alter table post modify nickname constraint post_nickname_nn not null;
alter table post modify pcontent constraint post_pcontent_nn not null;


insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기1','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기2','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기3','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기4','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기5','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기6','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기7','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기8','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기9','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기10','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기11','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기12','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기13','test1@kh.com','글쓴이1',3,'잼씀');
insert into post (post_id,pcategory,title,email,nickname,hit,pcontent) values (post_post_id_seq.nextval,'B0103','후기14','test1@kh.com','글쓴이1',3,'잼씀');
commit;
select * from post;

select t1.*
  from (select row_number() over (order by post_id desc) no, title
          from post) t1
 where t1.no between 2 and 5;
--delete from post;

-------------------------------------------------------------------------------------------
create table promotion(
    promotion_id number(10),
    post_id      number(10),
    ad_start_day  date,
    ad_end_day    date,
    ent_fee     varchar2(10)

);


--기본키
alter table promotion add Constraint promotion_promotion_id_pk primary key (promotion_id);

--외래키
--alter table post drop constraint post_bcategory_fk;
alter table promotion add constraint promotion_post_id_fk foreign key(post_id) references post(post_id);

drop sequence promotion_promotion_id_seq;
create sequence promotion_promotion_id_seq;

insert into promotion values (promotion_promotion_id_seq.nextval,1,'2022/10/25','2022/10/26','무료');
select * from promotion;
select *
  from promotion
 where post_id = 1;
select * from post;
commit;
select post_post_id_seq.currval
  from dual;
select *
  from post;
-----------------------------------------------------------------------------------------


--공연장 상세


create table p_facility(
    mt10id       VARCHAR2(10),   --   pk, fk, 공연시설ID
    fcltynm       VARCHAR2(100),  --   fk, 공연시설명
    mt13cnt       VARCHAR2(5),    --   공연장 수
    fcltychartr   VARCHAR2(30),   --   시설특성
    seatscale   VARCHAR2(10),   --   5   객석 수
    telno       VARCHAR2(15),   --   전화번호
    relateurl   VARCHAR2(100),  --   홈페이지
    adres       VARCHAR2(120),  --   주소
    opende       VARCHAR2(6),    --   개관연도
    la           VARCHAR2(20),   --   위도
    lo           VARCHAR2(25)    --   경도

);
alter table p_facility add constraint p_facility_mt10id_pk primary key(mt10id);


-------------------------------------------------------------------------------------------

--select * from p_event;
--delete from p_event;
--commit;

--공연전시 공공데이터

drop sequence p_event_post_id_seq;

create sequence p_event_post_id_seq;

create table p_event(
    event_id        NUMBER(10),     --  내부관리용 ID
    mt20id           VARCHAR2(10),   --  pk, not null   12   공연ID +
    prfnm           VARCHAR2(100),  --   공연명 +
    prfpdfrom       VARCHAR2(10),   --   공연시작일 +
    prfpdto           VARCHAR2(10),   --   공연종료일 +
    fcltynm           VARCHAR2(100),  --   공연시설명(공연장명) +
    prfcast           VARCHAR2(100),  --   공연출연진 +
    prfcrew           VARCHAR2(30),   --   공연제작진 +
    prfruntime       VARCHAR2(20),   --   공연 런타임 +
    prfage           VARCHAR2(20),   --   공연 관람 연령 +
    entrpsnm       VARCHAR2(50),   --  제작사 +
    pcseguidance   VARCHAR2(80),   --  티켓가격 +
    poster           VARCHAR2(100),  --   포스터이미지경로
    sty               CLOB,          --  줄거리
    genrenm           VARCHAR2(10),   --   공연 장르명 +
    prfstate       VARCHAR2(20),   --   공연상태 +
    openrun           VARCHAR2(1),    --   오픈런
    styurl1           VARCHAR2(100),  --   소개이미지1
    styurl2           VARCHAR2(100),  --   소개이미지2
    styurl3           VARCHAR2(100),  --   소개이미지3
    styurl4           VARCHAR2(100),  --   소개이미지4
    mt10id           VARCHAR2(10),   --   공연시설ID
    dtguidance       VARCHAR2(100)   --   공연시간 +
);
alter table p_event add constraint p_event_mt20id_pk primary key(mt20id);
--alter table p_event add constraint p_event_mt10id_fk foreign key(mt10id) references p_facility(mt10id);
--alter table p_event drop constraint p_event_mt10id_fk;
commit;
select t1.*
  from (select row_number() over (order by event_id desc) no, prfnm
          from p_event) t1
where t1.no between 3 and 8;
select count(*) from p_event;

select count(*)
  from p_event
 where to_date(prfpdto) > '2022.09.25';

select t1.*
  from (select row_number() over (order by prfpdto) no, poster
          from p_event
         where to_date(prfpdto) > '2022.09.25') t1
 where t1.no < 6;
-------------------------------------------------------------------------------------------

---------
--댓글
---------

drop table reply;
drop sequence reply_reply_id_seq;



create sequence reply_reply_id_seq;

create table reply(
  reply_id        number(10),
  post_id       number(10),
  pcategory       varchar2(11),
  email           varchar2(50),
  nickname        varchar2(30),
  rcontent        varchar2(100),
  cdate           timestamp default systimestamp,
  udate           timestamp default systimestamp
);
select * from reply;
insert into reply(reply_id,post_id,pcategory,email,nickname,rcontent) values (reply_reply_seq.nextval, 3, 'B0102', 'test1@kh.com', 'test1','잼씀');
commit;
--기본키
alter table reply add constraint reply_reply_id_pk primary key(reply_id);

--외래키
alter table reply add constraint reply_post_id_fk
    foreign key(post_id) references post(post_id);
alter table reply add constraint reply_email_fk
    foreign key(email) references member(email);
--제약조건



-------------------------------------------------------------------------------------------



drop sequence uploadfile_uploadfile_id_seq;

--시퀀스
create sequence uploadfile_uploadfile_id_seq;
---------
--첨부파일
---------
create table uploadfile(
    uploadfile_id   number(10),      --파일아이디
    code            varchar2(11),    --분류코드
    rid             varchar2(10),    --참조번호(게시글번호등)
    store_filename  varchar2(100),   --서버보관파일명
    upload_filename varchar2(100),   --업로드파일명(유저가 업로드한파일명)
    fsize           varchar2(45),    --업로드파일크기(단위byte)
    ftype           varchar2(100),   --파일유형(mimetype)
    cdate           timestamp default systimestamp, --등록일시
    udate           timestamp default systimestamp  --수정일시
);
--기본키
alter table uploadfile add constraint uploadfile_uploadfile_id_pk primary key(uploadfile_id);

--외래키
alter table uploadfile add constraint uploadfile_uploadfile_id_fk
    foreign key(code) references code(code_id);

--제약조건
alter table uploadfile modify code constraint uploadfile_code_nn not null;
alter table uploadfile modify rid constraint uploadfile_rid_nn not null;
alter table uploadfile modify store_filename constraint uploadfile_store_filename_nn not null;
alter table uploadfile modify upload_filename constraint uploadfile_upload_filename_nn not null;
alter table uploadfile modify fsize constraint uploadfile_fsize_nn not null;
alter table uploadfile modify ftype constraint uploadfile_ftype_nn not null;


-------------------------------------------------------------------------------------------



---------
--좋아요
---------

drop sequence good_good_id_seq;

create sequence good_good_id_seq;

create table good(
  good_id       number(10),
  post_id     number(10),
  email       varchar2(50),
  cdate           timestamp default systimestamp,
  udate           timestamp default systimestamp
);

--기본키
alter table good add constraint good_good_id_pk primary key(good_id);

--외래키
alter table good add constraint good_post_id_fk
    foreign key(post_id) references post(post_id);
alter table good add constraint good_email_fk
    foreign key(email) references member(email);
--======================================================================
commit;
--select * from member;
--select * from p_event;
--select * from promotion;
select *
  from reply
order by reply_id;
--
--select *
--  from reply
-- where post_id = 21
--order by reply_id desc;
--
--select reply_reply_id_seq.nextval
--  from dual;

create sequence reply_reply_seq;
alter table promotion drop constraint promotion_post_id_fk;
alter table reply drop constraint reply_post_id_fk;
alter table post drop constraint post_email_fk;
alter table reply drop constraint reply_email_fk;


commit;