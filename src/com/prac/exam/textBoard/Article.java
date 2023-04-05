package com.prac.exam.textBoard;

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
  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("{id : %d, regDate : %s, updateDate : %s, title : \"%s\", body : \"%s\"}", id, regDate, updateDate, title, body);
  }
}
