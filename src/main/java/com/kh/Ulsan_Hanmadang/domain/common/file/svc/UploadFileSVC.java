package com.kh.Ulsan_Hanmadang.domain.common.file.svc;

import com.kh.Ulsan_Hanmadang.domain.common.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadFileSVC {

  /**
   * 업로드파일 처리-단건
   * @param code  분류코드(ex 계시판,상품등록..)
   * @param fid   참조ID(ex 게시글번호, 상품번호..)
   * @param file  첨부파일
   * @return 성공여부
   */
  boolean addFile(String code, Long fid, MultipartFile file);
  /**
   * 업로드파일 처리-여러건
   * @param code  분류코드(ex 계시판,상품등록..)
   * @param fid   참조ID(ex 게시글번호, 상품번호..)
   * @param files  첨부파일
   * @return 성공여부
   */
  boolean addFile(String code, Long fid, List<MultipartFile> files);

  /**
   * 업로드파일 경로
   * @param code
   * @return
   */
  String getFullPath(String code);
  /**
   * 업로드파일조회
   * @param code
   * @param rid
   * @return
   */
  List<UploadFile> getFilesByCodeWithRid(String code, Long rid);

  /**
   * 첨부파일조회
   * @param  uploadfileId 첨부파일 아이디
   * @return 첨부파일 메타정보
   */
  UploadFile findFileByUploadFileId(Long uploadfileId);

  /**
   * 첨부파일 삭제 by uplaodfileId
   * @param uploadfileId 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByUploadFildId(Long uploadfileId);

  /**
   * 첨부파일 삭제 By code, rid
   * @param code 첨부파일 분류코드
   * @param rid 첨부파일아이디
   * @return 삭제한 레코드수
   */
  int deleteFileByCodeWithRid(String code, Long rid);

}