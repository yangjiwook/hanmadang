package com.kh.Ulsan_Hanmadang.web.interceptor;

import com.kh.Ulsan_Hanmadang.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String redirectUrl = null;
    String requestURI = request.getRequestURI();
    log.info("requestURI={}", requestURI);

    if(request.getQueryString() != null){
      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI)
              .append("&")
              .append(queryString)
              .toString();
    }else{
      redirectUrl = requestURI;
    }

    //세션정보가 있으면 반환 없으면 null반환
    HttpSession session = request.getSession(false);
    if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null ){
      response.sendRedirect("/login?redirectUrl=" + redirectUrl);
      log.info("REDIRECT_URL = /login?redirectUrl=" + redirectUrl);

      return false;
    }
    return true;
  }
}
