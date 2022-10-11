package com.kh.Ulsan_Hanmadang.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LOGInterceptor implements HandlerInterceptor {
  public static final String TRANSACION_ID = "transacionId";

  //컨트롤러 호출전
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    log.info("preHandle called!");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url

    String uuid = UUID.randomUUID().toString();  // 요청 추적번호
    request.setAttribute(TRANSACION_ID,uuid);

//    log.info("REQUEST [{}] [{}] [{}]", uuid,requestURI,handler);
    return true;
  }

  //컨트롤러 수행후 view가 렌더링되기전
  //컨트롤러에서 예외발생시 호출되지 않음.
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    log.info("postHandle called!");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url
    String uuid = (String)request.getAttribute(TRANSACION_ID);
//    log.info("REQUEST [{}] [{}] [{}]", uuid,requestURI,handler);
  }

  //view가 렌더링되고 응답메세지가 클라이언트에 전송된 후
  //컨트롤러에서 예외발생 유무에 상관없이 항상 호출됨.
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//    log.info("afterCompletion");
    String requestURI = request.getRequestURI(); //클라이언트의 요청 url
    String uuid = (String)request.getAttribute(TRANSACION_ID);
//    log.info("REQUEST [{}] [{}] [{}]", uuid,requestURI,handler);

    if(ex != null){
      log.error("afterCompletion error!!", ex);
    }
  }
}
