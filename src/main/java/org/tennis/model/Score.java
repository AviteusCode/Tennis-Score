package org.tennis.model;

public enum Score {
  ZERO("0"),
  FIFTEEN("15"),
  THIRTY("30"),
  FOURTY("40"),
  ADVANTAGE(""),
  WIN("");

  private String score;

  Score(String score) {
    this.score = score;
  }

  public String getScore() {
    return score;
  }
}
