package com.prac.exam.textBoard;

import java.util.Map;

public class Member {
  public int id;
  public String regDate;
  public String updateDate;
  public String loginId;
  public String loginPw;
  public String name;

  public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.loginId = loginId;
    this.loginPw = loginPw;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}