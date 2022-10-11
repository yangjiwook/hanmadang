package com.kh.Ulsan_Hanmadang.web.form.member;

public enum Gubun {
  M0101("M0101"), M0102("M0102"), M01A1("M01A1"), M01A2("M01A2");

  private final String description;

  Gubun(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}