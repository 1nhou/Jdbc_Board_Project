package com.prac.exam.textBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    List<Article> articles = new ArrayList<>();
    int articleLastId = 0;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();

      if (cmd.equals("add")) {
        System.out.println("== 게시물 생성 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        int id = ++articleLastId;

//      DB 연결
        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "root", "P@ssw0rd");


          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", title = \"" + title +"\"";
          sql += ",`body` = \"" + body +"\";";

          pstat = conn.prepareStatement(sql);
          int affectedRows = pstat.executeUpdate();

          System.out.println("affectedRows : " + affectedRows);

        } catch (ClassNotFoundException e) {
          System.out.println("드라이버 로딩 실패");
        } catch (SQLException e) {
          System.out.println("에러: " + e);
        } finally { //conn 하고 pstat close() 함수로 메모리 누수 막아주기
          try {
            if (conn != null && !conn.isClosed()) {
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }

        }
      }
    }
  }
}
