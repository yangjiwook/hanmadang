package com.kh.Ulsan_Hanmadang.domain.api.svc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;
import com.kh.Ulsan_Hanmadang.domain.api.dao.ApiPubDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiPubSVCImpl implements ApiPubSVC{

  private final ApiPubDAO apiPubDAO;

  private static final String serviceKey = "0f741e87bbe646ce800c16ffbaf9b69d";

  @Override
  public List<PEvent> apiCall(String fromDate, String toDate) {
    String xmlStr = "";

    List<PEvent> res = null;
    List<PEvent> ress = null;
    Set<String> facIds = new TreeSet<>();
//        String[] local_id = new String[]{"31","26","48"};   //울산(31),부산(26),경남(48)
    try {
// 1. URL을 만들기 위한 StringBuilder
      StringBuilder urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/pblprfr?service=" + serviceKey ); /*요청url, 공연정보*/
      // 2. 오픈 API의요청 규격에 맞는 파라미터 생성
      urlBuilder.append("&" + URLEncoder.encode("stdate", "UTF-8") + "=" + URLEncoder.encode(fromDate, "UTF-8")); /*시작일자*/
      urlBuilder.append("&" + URLEncoder.encode("eddate", "UTF-8") + "=" + URLEncoder.encode(toDate, "UTF-8")); /*마감일자*/
      urlBuilder.append("&" + URLEncoder.encode("rows", "UTF-8") + "=" + URLEncoder.encode("20", "UTF-8")); /*한 페이지 결과 수*/
      urlBuilder.append("&" + URLEncoder.encode("cpage", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
      urlBuilder.append("&" + URLEncoder.encode("signgucode", "UTF-8") + "=" + URLEncoder.encode("31", "UTF-8")); /*시구군 번호*/

      URL url = new URL(urlBuilder.toString());                           // 3. URL 객체 생성
//            System.out.println(url);
      xmlStr = getApiData(url);
      XmlMapper xmlMapper = new XmlMapper();
      //xml to java object
//            res = xmlMapper.readValue(xmlStr, (new ArrayList<PEvent>()).getClass());
      res = xmlMapper.readValue(xmlStr, new TypeReference<List<PEvent>>(){}); //공연ID로 공연상세정보 요청에 사용
//            System.out.println(res);
      //공연상세정보
      for (PEvent pEvent : res ) {
        urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/pblprfr/"+ pEvent.getMt20id() + "?service=" + serviceKey ); //공연상세
        url = new URL(urlBuilder.toString());
//                System.out.println(url);
        xmlStr = getApiData(url);
        ress = xmlMapper.readValue(xmlStr, new TypeReference<List<PEvent>>(){});
        facIds.add(apiPubDAO.savePEvent(ress)); //저장하면서 공연장ID 수집
      }
      apiCall2(facIds);   //공연장상세정보 획득/저장
    } catch (Exception e) {
      e.printStackTrace();
    }
    return res;
  }

  //공연장 상세 정보
  @Override
  public List<PFacility> apiCall2(Set<String> facIds) {
    String xmlStr = "";

    List<PFacility> res = null;
    try {

      for (String facId : facIds) {
        StringBuilder urlBuilder = new StringBuilder("http://www.kopis.or.kr/openApi/restful/prfplc/"+ facId +"?service=" + serviceKey); //공연장ID로 공연장상세정보요청
        URL url = new URL(urlBuilder.toString());
        xmlStr = getApiData(url);
        XmlMapper xmlMapper = new XmlMapper();
        res = xmlMapper.readValue(xmlStr, new TypeReference<List<PFacility>>(){});
        apiPubDAO.savePFacility(res);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return res;
  }


  public String getApiData(URL url) {

    String xmlStr = "";
    try {
      System.out.println(url);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // 4. 요청하고자 하는 URL과 통신하기 위한 Connection 객체 생성.
      conn.setRequestMethod("GET");                                       // 5. 통신을 위한 메소드 SET.
      conn.setRequestProperty("accept", "application/xml");               // 6. 통신을 위한 Content-type SET.
      System.out.println("Response code: " + conn.getResponseCode());     // 7. 통신 응답 코드 확인.
      BufferedReader rd;                                                  // 8. 전달받은 데이터를 BufferedReader 객체로 저장
      if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }
      StringBuilder sb = new StringBuilder();     // 9. 저장된 데이터를 라인별로 읽어 StringBuilder 객체로 저장.
      String line;
      while ((line = rd.readLine()) != null) {
        sb.append(line);
      }
      rd.close();
      conn.disconnect();
      xmlStr = sb.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return xmlStr;
  }

}
