package com.prac.exam.textBoard.Controller;

import com.prac.exam.textBoard.Article;
import com.prac.exam.textBoard.service.ArticleService;
import com.prac.exam.textBoard.util.DBUtil;
import com.prac.exam.textBoard.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController extends Controller{
  private ArticleService articleService;
  public ArticleController(Connection conn, Scanner sc) {
    super(sc);
    articleService = new ArticleService(conn);
  }

  public void add(String cmd) {
    System.out.println("== 게시물 생성 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    int id = articleService.add(title,body);
    System.out.printf("%d번 게시물이 생성 되었습니다.\n", id);

  }

  public void showList(String cmd) {

    List<Article> articles = articleService.getArticles();


    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }
    System.out.println("== 게시물 리스트 ==");
    System.out.println("번호 / 제목");
    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void showDetail(String cmd) {

    int id = Integer.parseInt(cmd.split(" ")[2]);

    Article article = articleService.getArticleById(id);

    if(article == null){
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("=== %d번 게시글 상세보기 ===\n", article.id);
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("현재 날짜 : %s\n", article.regDate);
    System.out.printf("수정 날짜 : %s\n", article.updateDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  public void modify(String cmd) {
    int id = Integer.parseInt(cmd.split(" ")[2]);

    System.out.printf("== %d번 게시물 수정 ==\n", id);
    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String body = sc.nextLine();

    articleService.update(id,title,body);
    System.out.printf("%d 번 게시글이 수정되었습니다.\n", id);
  }

   public void delete(String cmd) {
    int id = Integer.parseInt(cmd.split(" ")[2]);

    boolean articleExists = articleService.articleExists(id);

    if(articleExists == false){
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);
    System.out.printf("%d 번 게시글이 삭제되었습니다.\n", id);
  }
}
