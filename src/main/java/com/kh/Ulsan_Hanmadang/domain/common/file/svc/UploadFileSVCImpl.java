package com.kh.Ulsan_Hanmadang.domain.common.file.svc;

import com.kh.Ulsan_Hanmadang.domain.common.file.UploadFile;
import com.kh.Ulsan_Hanmadang.domain.common.file.dao.UploadFileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UploadFileSVCImpl implements UploadFileSVC{

  private final UploadFileDAO uploadFileDAO;

  //첨부파일 저장될 파일시스템의 경로 application.properties에 정의
  @Value("${attach.root_dir}")
  private String ROOT_DIR;  //첨부파일 루트경로

  @Override
  public boolean addFile(String code, Long fid, MultipartFile file) {
    try {
      UploadFile uploadFile = new UploadFile();
      uploadFile.setCode(code);
      uploadFile.setRid(fid);

      String originalFileName = file.getOriginalFilename();
      String storeFileName = createStoreFilename(originalFileName);
      uploadFile.setStore_filename(storeFileName);
      uploadFile.setUpload_filename(originalFileName);

      uploadFile.setFsize(String.valueOf(file.getSize()));
      uploadFile.setFtype(file.getContentType());

      //파일시스템에 물리적 파일 저장
      storeFile(uploadFile, file);
      //uploadfile 테이블에 첨부파일 메타정보 저장
      uploadFileDAO.addFile(uploadFile);

    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean addFile(String code, Long fid, List<MultipartFile> files) {
    //1) uploadfile 테이블에 첨부파일 메타정보 저장
    //2) 파일시스템에 물리적 파일 저장
    try {
      List<UploadFile> uploadFiles = new ArrayList<>();

      for(MultipartFile file: files) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setCode(code);
        uploadFile.setRid(fid);

        String originalFileName = file.getOriginalFilename();
        String storeFileName = createStoreFilename(originalFileName);
        uploadFile.setStore_filename(storeFileName);
        uploadFile.setUpload_filename(originalFileName);

        uploadFile.setFsize(String.valueOf(file.getSize()));
        uploadFile.setFtype(file.getContentType());

        uploadFiles.add(uploadFile);
      }
      storeFiles(uploadFiles, files);
      uploadFileDAO.addFile(uploadFiles);
    }catch (Exception e){
      e.printStackTrace();
      return false;
    }
    return true;
  }

  //첨부파일조회
  @Override
  public List<UploadFile> getFilesByCodeWithRid(String code, Long rid) {
    return uploadFileDAO.getFilesByCodeWithRid(code,rid);
  }
  //파일시스템에 물리적 파일 저장
  private void storeFile(UploadFile uploadFile, MultipartFile file) {
    try {
      file.transferTo(Path.of(getFullPath(uploadFile.getCode()), uploadFile.getStore_filename()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  //파일시스템에 물리적 파일 저장
  private void storeFiles(List<UploadFile> uploadFiles, List<MultipartFile> files) {
    for (int i=0; i<uploadFiles.size(); i++) {
      storeFile(uploadFiles.get(i), files.get(i));
    }
  }

  //파일저장경로
  @Override
  public String getFullPath(String code) {
    StringBuffer path = new StringBuffer();
    path = path.append(ROOT_DIR).append(code).append("/");
    //경로가 없으면 생성
    createFolder(path.toString());
//    log.info("파일저장위치={}", path.toString());
    return path.toString();
  }

  //첨부파일조회
  @Override
  public UploadFile findFileByUploadFileId(Long uploadfileId) {
    return uploadFileDAO.findFileByUploadFileId(uploadfileId);
  }

  // 첨부파일 삭제 by uplaodfileId
  @Override
  public int deleteFileByUploadFildId(Long uploadfileId){

    //1)물리파일삭제
    UploadFile uploadFile = uploadFileDAO.findFileByUploadFileId(uploadfileId);
    deleteFile(uploadFile.getCode(), uploadFile.getStore_filename());

    //2)메타정보삭제
    int affectedRow = uploadFileDAO.deleteFileByUploadFildId(uploadfileId);

    return affectedRow;
  }

  // 첨부파일 삭제 By code, rid
  public int deleteFileByCodeWithRid(String code, Long rid){

    //1)물리파일삭제
    List<UploadFile> uploadFiles = uploadFileDAO.getFilesByCodeWithRid(code, rid);
    for (UploadFile uploadFile : uploadFiles) {
      deleteFile(uploadFile.getCode(), uploadFile.getStore_filename());
    }

    //2)메타정보삭제
    uploadFileDAO.deleteFileByCodeWithRid(code, rid);

    return uploadFiles.size();
  }



  /**
   * 서버 보관 파일 삭제
   * @param code
   * @param sfname
   * @return
   */
  private boolean deleteFile(String code ,String sfname) {

    boolean isDeleted = false;

    File file = new File(getFullPath(code)+sfname);

    if(file.exists()) {
      if(file.delete()) {
        isDeleted = true;
      }
    }

    return isDeleted;
  }

  private boolean deleteFiles(String code, List<String> fnames ) {

    boolean isDeleted = false;
    int deletedFileCount = 0;

    for(String sfname : fnames) {
      if(deleteFile(code, sfname)) {
        deletedFileCount++;
      };
    }

    if(deletedFileCount == fnames.size()) isDeleted = true;

    return isDeleted;
  }

  //폴더생성
  private void createFolder(String path) {
    File folder = new File(path);
    if(!folder.exists()){
      folder.mkdir();
    }
  }

  //임의파일명 생성
  private String createStoreFilename(String originalFile) {
    StringBuffer storeFileName = new StringBuffer();
    storeFileName.append(UUID.randomUUID().toString())
            .append(".")
            .append(extractExt(originalFile)); // xxx-yyy-zzz-ttt..
    return storeFileName.toString();
  }

  //확장자 추출
  private String extractExt(String originalFile) {
    int posOfExt =originalFile.lastIndexOf(".");
    String ext = originalFile.substring(posOfExt + 1);
    return ext;
  }


}
