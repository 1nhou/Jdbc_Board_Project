package com.prac.exam.textBoard.Controller;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn;
  protected Scanner sc;

  public void setConn(Connection conn) {
    this.conn = conn;
  }

  public void setScanner(Scanner sc) {
    this.sc = sc;
  }
}
