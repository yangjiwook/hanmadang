package com.kh.Ulsan_Hanmadang.domain.api.svc;

import com.kh.Ulsan_Hanmadang.domain.PEvent;
import com.kh.Ulsan_Hanmadang.domain.PFacility;

import java.util.List;
import java.util.Set;

public interface ApiPubSVC {
  List<PEvent> apiCall(String fromDate, String toDate);

  List<PFacility> apiCall2(Set<String> facIds);
}

