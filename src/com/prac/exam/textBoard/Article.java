package com.prac.exam.textBoard;

import java.util.Map;

public class Article {
  public int id;
  public String title;
  public String body;
  public String regDate;
  public String updateDate;

  public Article(int id, String title, String body, String regDate, String updateDate) {
    this.id = id;
    this.title = title;
    this.body = body;
    this.regDate = regDate;
    this.updateDate = updateDate;
  }

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
  }

  @Override
  public String toString() {
    return String.format("{id : %d, regDate : %s, updateDate : %s, title : \"%s\", body : \"%s\"}", id, regDate, updateDate, title, body);
  }
}
