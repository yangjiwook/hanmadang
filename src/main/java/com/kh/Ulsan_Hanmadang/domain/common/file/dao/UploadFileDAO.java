package com.kh.Ulsan_Hanmadang.domain.common.file.dao;

import com.kh.Ulsan_Hanmadang.domain.common.file.UploadFile;

import java.util.List;

public interface UploadFileDAO {
  /**
   * 업로드 파일 등록 - 단건
   * @param uploadFile
   * @return 파일Id
   */
  Long addFile(UploadFile uploadFile);

  /**
   * 업로드 파일 등록 - 여러건
   * @param uploadFile
   */
  void addFile(List<UploadFile> uploadFile);

  /**
   * 업로드파일조회
   * @param code
   * @param rid
   * @return
   */
  List<UploadFile> getFilesByCodeWithRid(String code, Long rid);

  /**
   * 첨부파일조회
   * @param uploadfileId
   * @return
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
