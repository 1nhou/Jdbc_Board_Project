package com.prac.exam.textBoard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;

    while (true) {
      System.out.printf("명령어) ");
      String cmd = sc.nextLine();
      cmd = cmd.trim();

      if (cmd.equals("add")) {
        System.out.println("== 게시물 생성 ==");
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        Connection conn = null;

        PreparedStatement pstat = null;
        try{
          Class.forName("com.mysql.jdbc.Driver");
          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
          conn = DriverManager.getConnection(url, "root", "P@ssw0rd");
          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", `body` = \"" + body + "\";";

          pstat = conn.prepareStatement(sql);
          pstat.executeUpdate();

        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
          try {
            if(pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
      if (cmd.startsWith("modify ")) {
        int id = Integer.parseInt(cmd.split(" ")[1]);

        System.out.printf("== %d번 게시물 수정 ==\n", id);
        System.out.printf("새 제목 : ");
        String title = sc.nextLine();
        System.out.printf("새 내용 : ");
        String body = sc.nextLine();

        Connection conn = null;

        PreparedStatement pstat = null;
        try{
          Class.forName("com.mysql.jdbc.Driver");
          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
          conn = DriverManager.getConnection(url, "root", "P@ssw0rd");
          String sql = "UPDATE article";
          sql += " SET updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", `body` = \"" + body + "\"";
          sql += "WHERE id = " + id;

          pstat = conn.prepareStatement(sql);
          pstat.executeUpdate();

        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
          try {
            if(pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        System.out.printf("%d 번 게시글이 수정되었습니다.\n",id);
      }

      else if (cmd.equals("list")) {

        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;

        List<Article> articles = new ArrayList<>();

        try{
          Class.forName("com.mysql.jdbc.Driver");

          String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

          conn = DriverManager.getConnection(url, "root", "P@ssw0rd");

          String sql = "SELECT *";
          sql += " FROM article";
          sql += " ORDER BY id DESC";

          pstat = conn.prepareStatement(sql);
          rs = pstat.executeQuery(sql);

          while (rs.next()) {
            int id = rs.getInt("id");

            String regDate = rs.getString("regDate");
            String updateDate = rs.getString("updateDate");
            String title = rs.getString("title");
            String body = rs.getString("body");

            Article article = new Article(id, regDate, updateDate, title, body);
            articles.add(article);
          }

        }
        catch(ClassNotFoundException e){
          System.out.println("드라이버 로딩 실패");
        }
        catch(SQLException e){
          System.out.println("에러: " + e);
        }
        finally{
          try {
            if(rs != null && !rs.isClosed()) {
              rs.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if(pstat != null && !pstat.isClosed()) {
              pstat.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try{
            if( conn != null && !conn.isClosed()){
              conn.close();
            }
          }
          catch( SQLException e){
            e.printStackTrace();
          }
        }

        if(articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        System.out.println("== 게시물 리스트 ==");
        System.out.println("번호 / 제목");
        for(Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }
      }
      else if (cmd.equals("exit")) {
        System.out.println("시스템 종료");
        break;
      }
      else {
        System.out.println("명령어를 확인해주세요.");
      }
    }
  }
}