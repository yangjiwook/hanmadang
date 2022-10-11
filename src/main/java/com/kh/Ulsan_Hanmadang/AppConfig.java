package com.kh.Ulsan_Hanmadang;

import com.kh.Ulsan_Hanmadang.web.interceptor.LOGInterceptor;
import com.kh.Ulsan_Hanmadang.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //모든요청에 대한 log
    registry.addInterceptor(new LOGInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/error");


    registry.addInterceptor(new LoginCheckInterceptor())
            .order(1)                   //인터셉터 실행 순저지정
            .addPathPatterns("/**")     // 인터셉터에 포함시키는 url패턴,루트경로부터 하위경로 모두
            .excludePathPatterns(
                "/"                  //초기화면
                ,"/css/**"
                ,"/js/**"
                ,"/static/js/**"
                ,"/img/**"
                ,"/error/**"
                ,"/login"                   //로그인
                ,"/logout"                  //로그아웃
                ,"/afterLogin"              // 로그인 이후 화면
                ,"/members/sign_in"         // 회원가입 화면
                ,"/members/leaveClear"      // 회원탈퇴 화면
                ,"/members/findByEmail"
                ,"/members/findByPassword"
                ,"/bbs/list/**"             // 게시판 (이전버전?)
                ,"/webjars/**"              //
                ,"/api/**"
                ,"/map"                     // 지도
                ,"/post/**"                 // 게시판
                ,"/tmp/**"
                ,"/attach/**"


            );  // 인테셉터에서 제외되는 url패턴
  }

  //cors허용하기위한 글로벌 설정
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")            //요청url
            .allowedOrigins("http://192.168.0.141:5500","http://localhost:5500")    //요청 client
            .allowedMethods("*")                            //모든 메소드
            .maxAge(3000);                                  //캐쉬시간
  }
}
