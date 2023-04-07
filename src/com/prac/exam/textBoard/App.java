package com.prac.exam.textBoard;

import com.prac.exam.textBoard.Controller.ArticleController;
import com.prac.exam.textBoard.Controller.MemberController;
import com.prac.exam.textBoard.util.DBUtil;
import com.prac.exam.textBoard.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    String cmd;

    while (true) {
      System.out.printf("명령어) ");
      cmd = sc.nextLine();
      cmd = cmd.trim();

      // DB 연결 시작
      Connection conn = null;
      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 드라이버 클래스가 없습니다. ");
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

      try {
        conn = DriverManager.getConnection(url, "root", "P@ssw0rd");


        // 핵심 로직
        int actionResult = action(conn, sc, cmd);

        if (actionResult == -1) {
          break;
        }

      } catch (SQLException e) {
        System.out.println("예외 : DB에 연결할 수 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      } finally {
        try {
          if (conn != null && !conn.isClosed()) {
            conn.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
      // DB 연결 끝
    }
    sc.close();
  }
  private int action(Connection conn, Scanner sc, String cmd) {
    ArticleController articleController = new ArticleController(conn, sc);
    MemberController memberController = new MemberController(conn,sc);


    if(cmd.equals("member join")){
      memberController.join(cmd);
    }
      else if (cmd.equals("article add")) {
        articleController.add(cmd);
    }
    else if (cmd.equals("article list")) {
      articleController.showList(cmd);
    }
      else if (cmd.startsWith("article detail ")) {
        articleController.showDetail(cmd);
    }
      else if (cmd.startsWith("article modify ")) {
        articleController.modify(cmd);
    }
      else if (cmd.startsWith("article delete ")) {
        articleController.delete(cmd);
    }
      else if (cmd.equals("exit")) {
      System.out.println("시스템 종료");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return 0;

  }
}
