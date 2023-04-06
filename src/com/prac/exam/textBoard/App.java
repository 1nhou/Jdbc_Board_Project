package com.prac.exam.textBoard;

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

        int actionResult = doAction(conn, sc, cmd);

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
  private int doAction(Connection conn, Scanner sc, String cmd) {
      if (cmd.equals("join")) {
        String loginId;
        String loginPw;
        String loginPwConfirm;
        String name;



      System.out.println("== 회원 가입 ==");

      // 로그인 아이디 입력
      while (true) {
        System.out.printf("로그인 아이디 : ");
        loginId = sc.nextLine().trim();

        if(loginId.length() == 0){
          System.out.println("로그인 아이디를 입력해주세요.");
          continue;
        }

        // 로그인 아이디 검증
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ?", loginId);

        boolean loginedDup = DBUtil.selectRowBooleanValue(conn, sql);

        if(loginedDup) {
          System.out.printf("%s(은)는 이미 사용중인 아이디 입니다.\n", loginId);
          continue;
        }


        break;
      }

      // 로그인 비밀번호 입력
      while (true) {
        System.out.printf("로그인 비밀번호 : ");
        loginPw = sc.nextLine().trim();

        if(loginPw.length() == 0){
          System.out.println("로그인 비밀번호를 입력해주세요.");
          continue;
        }

        // 비밀번호 검증
        boolean loginPwConfirmIsSame = true;

        while (true) {
          System.out.printf("로그인 비밀번호 확인 : ");
          loginPwConfirm = sc.nextLine().trim();

          if(loginPw.length() == 0){
            System.out.println("로그인 비밀번호를 한번 더 입력해주세요.");
            continue;
          }
          if(loginPw.equals(loginPwConfirm) == false) {
            System.out.println("비밀번호가 일치하지 않습니다. 다시 입력 해주세요.");
            loginPwConfirmIsSame = false;
            break;
          }
          break;
        }
        if(loginPwConfirmIsSame) {
          break;
        }
      }

        //  이름 입력
        while (true) {
          System.out.printf("이름 : ");
          name = sc.nextLine().trim();

          if(name.length() == 0){
            System.out.println("이름을 입력해주세요.");
            continue;
          }
          break;
        }

      SecSql sql = new SecSql();
      sql.append("INSERT INTO `member`");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?", loginId);
      sql.append(", loginPw = ?", loginPw);
      sql.append(", name = ?", name);

      int id = DBUtil.insert(conn, sql);
      System.out.printf("%d번 회원이 생성 되었습니다.\n", id);
    }
      else if (cmd.equals("add")) {
      System.out.println("== 게시물 생성 ==");
      System.out.printf("제목 : ");
      String title = sc.nextLine();
      System.out.printf("내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(",`body` = ?", body);

      int id = DBUtil.insert(conn, sql);
      System.out.printf("%d번 게시물이 생성 되었습니다.\n", id);

    }
    else if (cmd.equals("list")) {
      List<Article> articles = new ArrayList<>();

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

      for(Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
      }

      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return 0;
      }
      System.out.println("== 게시물 리스트 ==");
      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    }
      else if (cmd.startsWith("detail ")) {
      int id = Integer.parseInt(cmd.split(" ")[1]);

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMaps = DBUtil.selectRow(conn,sql);

      if(articleMaps.isEmpty()){
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return 0;
      }

      Article article = new Article(articleMaps);
      System.out.printf("=== %d번 게시글 상세보기 ===\n", article.id);
      System.out.printf("번호 : %d\n", article.id);
      System.out.printf("현재 날짜 : %s\n", article.regDate);
      System.out.printf("수정 날짜 : %s\n", article.updateDate);
      System.out.printf("제목 : %s\n", article.title);
      System.out.printf("내용 : %s\n", article.body);
    }
      else if (cmd.startsWith("modify ")) {
      int id = Integer.parseInt(cmd.split(" ")[1]);

      System.out.printf("== %d번 게시물 수정 ==\n", id);
      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(",`body` = ?", body);
      sql.append("WHERE id = ?", id);

      DBUtil.update(conn, sql);
      System.out.printf("%d 번 게시글이 수정되었습니다.\n", id);
    }
      else if (cmd.startsWith("delete ")) {
      int id = Integer.parseInt(cmd.split(" ")[1]);

      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articleCount = DBUtil.selectRowIntValue(conn,sql);

      if(articleCount == 0){
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return 0;
      }

      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      DBUtil.delete(conn,sql);

      System.out.printf("%d 번 게시글이 삭제되었습니다.\n", id);
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
