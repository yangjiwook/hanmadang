package com.kh.Ulsan_Hanmadang.web.admin;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.admin.svc.AdminPostSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/posts")
public class AdminPostController {

  private final AdminPostSVC adminPostSVC;

  //공연목록
  @GetMapping("/pub/event/list")
  public String all(Model model) {
    List<PEvent> list = adminPostSVC.pEventList();

    model.addAttribute("list", list);

    return "admin/post/pEventList";
  }

}
