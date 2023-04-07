package com.prac.exam.textBoard.Controller;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection conn;
  protected Scanner sc;

  public Controller(Scanner sc) {
    this.sc = sc;
  }
}
