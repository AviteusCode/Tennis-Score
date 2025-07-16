package org.tennis;

import org.tennis.service.TennisScoreService;

public class Main {

  public static void main(String[] args) {
    TennisScoreService service = new TennisScoreService();
    service.computeScore("ABABAA");
  }
}
